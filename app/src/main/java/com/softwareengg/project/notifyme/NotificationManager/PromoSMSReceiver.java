package com.softwareengg.project.notifyme.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shivanshu on 31/03/17.
 * This class has been registered to be notified when a SMS is received.
 * Purpose:
 * - Receives SMSes.
 * - checks if the SMS is a Promo from a supported Vendor.
 * - forwards Promos to PromoManager Module for processing.
 */

public class PromoSMSReceiver extends BroadcastReceiver {

    private static final String TAG = "NotifyMe";
    private final SmsManager sms = SmsManager.getDefault();
    public Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        Log.v(TAG, "PromoSMSReceiver : onReceive");
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i(TAG, "PromoSMSReceiver: senderNum: " + senderNum + ", message: " + message);

                    // Invokes the PromoManager to process this message
                    Intent newPromoRecvd = new Intent(context, PromoManager.class);
                    newPromoRecvd.putExtra("package", "");
                    newPromoRecvd.putExtra("ticker", senderNum);
                    newPromoRecvd.putExtra("title", senderNum);
                    newPromoRecvd.putExtra("text", message);
                    context.startService(newPromoRecvd);
                } // end for loop
            } // bundle is null
        } catch (Exception e) {
            Log.e(TAG, "PromoSMSReceiver: " + e);
        }
    }

}
