package com.softwareengg.project.notifyme.NotificationManager;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.NotificationCompat;

import com.softwareengg.project.notifyme.NotifyMeContract.PromoEntry;
import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.PromoDatabaseHelper;
import com.softwareengg.project.notifyme.textprocess.TextProcessing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Deepanker Mishra on 04-04-2017.
 */

public class PromoManager extends IntentService{

    public final int SCORE_THRESHOLD = 100;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    PromoDatabaseHelper dbHelper;
    public PromoManager(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //it is a service
        //store parsed promo in db:PROMOS_TABLE
        //through notification if >threshold
        String promoText = intent.getStringExtra("text");
        promoText = promoText.replaceAll("[.,!\n]", " ");
        String lowerPromoText = promoText.toLowerCase();
        String[] lowPromoTextSplit = lowerPromoText.split(" +");
        Promo promo = TextProcessing.parsePromo(lowPromoTextSplit,promoText);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        ContentValues values = new ContentValues();
        values.put(PromoEntry.COLUMN_NAME_CATEGORY, promo.getCategory());
        values.put(PromoEntry.COLUMN_NAME_CODE, promo.getCode());
        values.put(PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT,promo.getDiscountAmount());
        values.put(PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT,promo.getDiscountPercentage());
        values.put(PromoEntry.COLUMN_NAME_EXPIRY, sdf.format(promo.getExpiry()));
        values.put(PromoEntry.COLUMN_NAME_MAX_USES, promo.getMaxUses());
        values.put(PromoEntry.COLUMN_NAME_PROMO_MSG, promo.getPromoMsg());
        values.put(PromoEntry.COLUMN_NAME_VENDOR,promo.getVendor());
        values.put(PromoEntry.COLUMN_NAME_RECEIPT, sdf.format(new Date()));

        dbHelper = PromoDatabaseHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(PromoEntry.TABLE_NAME,null,values);

        if(promo.getScore()>=SCORE_THRESHOLD){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            //builder.setSmallIcon(getResources().getDrawable(R.drawable.noti) R.drawable.notification_icon);
            builder.setContentTitle("Notification Alert, Click Me!");
            builder.setContentText("Hi, This is Android Notification Detail!");
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

}
