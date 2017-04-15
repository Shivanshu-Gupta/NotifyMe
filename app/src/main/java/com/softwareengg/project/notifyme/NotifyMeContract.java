package com.softwareengg.project.notifyme;

import android.provider.BaseColumns;

/**
 * Created by shivanshu on 15/04/17.
 */

public final class NotifyMeContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NotifyMeContract() {}

    /* Inner class that defines the Promo table contents */
    public static class PromoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Promos";
        public static final String COLUMN_NAME_CATEGORY ="category";
        public static final String COLUMN_NAME_VENDOR ="vendor";
        public static final String COLUMN_NAME_RECEIVEDON ="receviedOn";
        public static final String COLUMN_NAME_TYPE ="type";
        public static final String COLUMN_NAME_DISCOUNT = "discount";
        public static final String COLUMN_NAME_CODE ="code";
        public static final String COLUMN_NAME_EXPIRY ="expiry";
        public static final String COLUMN_NAME_CONTACT ="contact";
        public static final String COLUMN_NAME_LOCATION ="location";
        public static final String COLUMN_NAME_MAX_USES ="maxUses";
        public static final String COLUMN_NAME_PROMO_MSG ="promoMsg";
    }

}
