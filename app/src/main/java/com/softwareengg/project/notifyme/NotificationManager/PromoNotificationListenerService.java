package com.softwareengg.project.notifyme.NotificationManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by shivanshu on 31/03/17.
 * Purpose:
 * - Listens to notifications from other apps;
 * - checks if the notification is a Promo from a supported Vendor.
 * - forwards Promos to Scoring Module for processing.
 */


public class PromoNotificationListenerService extends NotificationListenerService {
    private static final String TAG = "NotifyMe";

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String ticker = "";
            if(sbn.getNotification().tickerText != null) {
                ticker = sbn.getNotification().tickerText.toString();
            }
            Bundle extras = sbn.getNotification().extras;
            String text = extras.getCharSequence("android.text").toString();
            String title = extras.getString("android.title");

            Log.i(TAG, "Package: " + pack);
            Log.i(TAG, "Ticker: " + ticker);
            Log.i(TAG, "Title: " + title);
            Log.i(TAG, "Text: " + text);

            if(isFromSupportedVendor(extras)) {
                Intent newPromoRecvd = new Intent("Msg");
                newPromoRecvd.putExtra("package", pack);
                newPromoRecvd.putExtra("ticker", ticker);
                newPromoRecvd.putExtra("title", title);
                newPromoRecvd.putExtra("text", text);
                LocalBroadcastManager.getInstance(context).sendBroadcast(newPromoRecvd);
            }
        }

        
    }

    private boolean isFromSupportedVendor(Bundle extras) {
        // TODO: check from database if the notification is a promo from a supported Vendor.

        return true;
    }
}

