package com.softwareengg.project.notifyme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shivanshu Gupta on 19-Sep-15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "NotifyMe";
    private static DatabaseHelper db;
    public static final String DATABASE_NAME="notifyme.db";
    public static String DATABASE_PATH = null;
    private static final int SCHEMA=1;
    static final String CATEGORY ="category";
    static final String VENDOR ="vendor";
    static final String RECEIVEDON ="receviedOn";
    static final String TYPE ="type";
    static final String DISCOUNT = "discount";
    static final String CODE ="code";
    static final String EXPIRY ="expiry";
    static final String CONTACT ="contact";
    static final String LOCATION ="location";
    static final String MAX_USES ="maxUses";
    static final String PROMO_MSG ="promoMsg";
    static final String PROMOS_TABLE ="Promos";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(db ==null) {
            db = new DatabaseHelper(context.getApplicationContext());
        }
        return db;
    }

    public static synchronized DatabaseHelper getInstanceFresh(Context context) {
        db.close();
        db = new DatabaseHelper(context.getApplicationContext());
        Cursor cur = db.getReadableDatabase().rawQuery("SELECT  * FROM " + PROMOS_TABLE, null);
        Log.v(TAG, "DatabaseHelper : File Count: " + cur.getCount());
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PROMOS_TABLE + " (" +
                CATEGORY + "TEXT NOT NULL, " +
                VENDOR + "TEXT NOT NULL, " +
                RECEIVEDON + "DATETIME NOT NULL, " +
                TYPE + "INT NOT NULL, " +
                DISCOUNT + "TEXT NOT NULL, " +
                CODE + "TEXT NOT NULL, " +
                EXPIRY + "DATETIME NOT NULL, " +
                CONTACT + "TEXT NOT NULL, " +
                LOCATION + "TEXT NOT NULL, " +
                MAX_USES + "INT NOT NULL, " +
                PROMO_MSG + "TEXT NOT NULL)"
        );
//        db.execSQL("CREATE TABLE fileSizes (cloudFileName TEXT, size REAL, cloudList TEXT)");
//        ContentValues cv=new ContentValues();
//
//        cv.put(FILENAME, "This");
//        cv.put(SIZE, 4);
//        db.insert(TABLE, FILENAME, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
