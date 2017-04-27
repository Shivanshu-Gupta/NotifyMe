package com.softwareengg.project.notifyme.NotificationManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.softwareengg.project.notifyme.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shivanshu on 31/03/17.
 *
 * Purpose:
 * - Listens to notifications from other apps;
 * - checks if the notification is a Promo from a supported Vendor.
 * - forwards Promos to PromoManager for processing.
 */


public class PromoNotificationListenerService extends NotificationListenerService {
    private static final String TAG = "NotifyMe";

    public Context context;
    private Set<String> supportedVendors;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        String[] vendors = getResources().getStringArray(R.array.vendors);
        Set<String> supportedVendors = new HashSet<>();
        for(String vendor : vendors) {
            supportedVendors.add(vendor);
        }
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

            // Invoke the PromoManager to process this message if the notification is from a
            // supported vendor app.
            if(isFromSupportedVendor(extras)) {
                Intent newPromoRecvd = new Intent(context, PromoManager.class);
                newPromoRecvd.putExtra("package", pack);
                newPromoRecvd.putExtra("ticker", ticker);
                newPromoRecvd.putExtra("title", title);
                newPromoRecvd.putExtra("text", text);
                context.startService(newPromoRecvd);
            }
        }
        
    }

    private boolean isFromSupportedVendor(Bundle extras) {
        return supportedVendors.contains(extras.getString("package"));
    }
}

