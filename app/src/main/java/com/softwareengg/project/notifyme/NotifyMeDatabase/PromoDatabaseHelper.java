package com.softwareengg.project.notifyme.NotifyMeDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.softwareengg.project.notifyme.NotifyMeDatabase.NotifyMeContract.PromoEntry;

/**
 * Created by shivanshu on 19-Sep-15.
 *
 * Purpose
 * - helper class for database accesses.
 */
public class PromoDatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "NotifyMe";
    private static PromoDatabaseHelper db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="notifyme.db";
    private PromoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // The right way to create an instance of this class
    // Ensures only one instance exists at any time.
    public static synchronized PromoDatabaseHelper getInstance(Context context) {
        if(db == null) {
            db = new PromoDatabaseHelper(context.getApplicationContext());
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the PromosTable if it hasn't been created yet.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PromoEntry.TABLE_NAME + " (" +
                PromoEntry._ID + " INTEGER PRIMARY KEY, " +
                PromoEntry.COLUMN_NAME_CATEGORY + " TEXT, " +
                PromoEntry.COLUMN_NAME_VENDOR + " TEXT NOT NULL, " +
                PromoEntry.COLUMN_NAME_CODE + " TEXT, " +
                PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT + " TEXT, " +
                PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT + " TEXT, " +
                PromoEntry.COLUMN_NAME_MAX_USES + " INT, " +
                PromoEntry.COLUMN_NAME_SCORE + " DECIMAL NOT NULL, " +
                PromoEntry.COLUMN_NAME_RECEIPT + " STRING NOT NULL, " +
                PromoEntry.COLUMN_NAME_EXPIRY + " STRING, " +
                PromoEntry.COLUMN_NAME_PROMO_MSG + " TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
