package com.softwareengg.project.notifyme;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.sql.Date;
import java.text.DateFormat;

/**
 * Created by startup on 24-04-2017.
 */


public class MessageDialog extends DialogFragment {
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_RECEIPT = "receipt";
    private static final String ARG_CODE = "code";
    private static final String ARG_DISCOUTAMOUNT = "discountAmount";
    private static final String ARG_DISCOUTPERCENTAGE = "discountPercentage";
    private Date mReceipt;
    private static final String ARG_EXPIRY = "expiry";
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_VENDOR = "vendor";
    private static final String ARG_SCORE = "score";
    public MessageDialog(){
    }
    public static MessageDialog newInstance(Promo promo) {
        MessageDialog fragment = new MessageDialog();
        Bundle args = new Bundle();
        if(promo.getCategory() != null) args.putString(ARG_CATEGORY, promo.getCategory());
        if(promo.getCode() != null) args.putString(ARG_CODE, promo.getCode());
        DateFormat df = DateFormat.getDateInstance();
        if(promo.getExpiry() != null) args.putString(ARG_EXPIRY, df.format(promo.getExpiry()));
        if(promo.getReceipt() != null) args.putString(ARG_RECEIPT, df.format(promo.getReceipt()));
        if(promo.getDiscountAmount() != 0) args.putDouble(ARG_DISCOUTAMOUNT, promo.getDiscountAmount());
        if(promo.getDiscountAmount() != 0) args.putDouble(ARG_DISCOUTPERCENTAGE, promo.getDiscountPercentage());
        if(promo.getPromoMsg() != null) args.putString(ARG_MESSAGE, promo.getPromoMsg());
        if(promo.getScore()!=0)args.putDouble(ARG_SCORE, promo.getScore());
        if(promo.getVendor()!=null)args.putString(ARG_VENDOR,promo.getVendor());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title

        if(getArguments()!=null){
            builder.setTitle(getArguments().getString(ARG_VENDOR));
            //TODO:add receipt date
            builder.setMessage(getArguments().getString(ARG_MESSAGE));
        }
        return builder.create();
    }
}
