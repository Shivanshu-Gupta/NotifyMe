package com.softwareengg.project.notifyme.NotificationManager;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.softwareengg.project.notifyme.NotifyMeDatabase.NotifyMeContract.PromoEntry;
import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.NotifyMeDatabase.PromoDatabaseHelper;
import com.softwareengg.project.notifyme.R;
import com.softwareengg.project.notifyme.textprocess.TextProcessing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Deepanker Mishra on 04-04-2017.
 */

public class PromoManager extends IntentService implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = "NotifyMe";

    PromoDatabaseHelper dbHelper;
    private SharedPreferences mSharedPreferences;
    private boolean notify;
    private Set<String> vendors;
    public final int SCORE_THRESHOLD = 100;

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public PromoManager() {
        super("PromoManager");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PromoManager(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //it is a service
        //store parsed promo in db:PROMOS_TABLE
        //through notification if >threshold
        String promoMsg = intent.getStringExtra("text");
        Log.v(TAG, "new message received: " + promoMsg);
        Promo promo = TextProcessing.parsePromo(promoMsg, getResources().getStringArray(R.array.vendors));
        if(promo == null) {
            // NOT A PROMO
            return;
        }
        Log.v(TAG, "new promo received: " + promoMsg);

        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
        ContentValues values = new ContentValues();
        //put values that need to be passed to the database
        values.put(PromoEntry.COLUMN_NAME_CATEGORY, promo.getCategory());
        if(promo.getCode() != null) values.put(PromoEntry.COLUMN_NAME_CODE, promo.getCode());
        values.put(PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT,promo.getDiscountAmount());
        values.put(PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT,promo.getDiscountPercentage());
        if(promo.getExpiry() != null) values.put(PromoEntry.COLUMN_NAME_EXPIRY, sdf.format(promo.getExpiry()));
        values.put(PromoEntry.COLUMN_NAME_MAX_USES, promo.getMaxUses());
        values.put(PromoEntry.COLUMN_NAME_PROMO_MSG, promo.getPromoMsg());
        if(intent.getStringExtra("package") != null && !intent.getStringExtra("package").equals("")) {
            values.put(PromoEntry.COLUMN_NAME_VENDOR, intent.getStringExtra("package"));
        } else {
            values.put(PromoEntry.COLUMN_NAME_VENDOR,promo.getVendor());
        }
        values.put(PromoEntry.COLUMN_NAME_SCORE,promo.getScore());
        values.put(PromoEntry.COLUMN_NAME_RECEIPT, sdf.format(new Date()));

        dbHelper = PromoDatabaseHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(PromoEntry.TABLE_NAME,null,values);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        notify = mSharedPreferences.getBoolean("notify", true);
        vendors = mSharedPreferences.getStringSet("vendors", new HashSet<String>());
        if(notify && promo.getScore()>=SCORE_THRESHOLD && vendors.contains(promo.getVendor())){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Notification Alert, Click Me!");
            builder.setContentText("Hi, This is Android Notification Detail!");
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        notify = mSharedPreferences.getBoolean("notify", true);
        vendors = mSharedPreferences.getStringSet("vendors", new HashSet<String>());
    }

}
