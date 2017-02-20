package com.softwareengg.project.notifyme;

import java.sql.Date;

/**
 * Created by shivanshu on 20/02/17.
 */

public class Promo {
    private static final String TAG = "NotifyMe";

    private enum PromoCategory {
        FOOD, CAB, TRAVEL, CLOTHING, ACCESSORIES, TALKTIME, DATA
    };

    private enum PromoType {
        BUY_X_GET_Y, DISCOUNT_BY_FIXED, DISCOUNT_BY_PERCENT, DISCOUNT_TO_FIXED
    };

    private PromoCategory category;
    private String vendor;
    private Date receivedOn;
    private PromoType type;

    private float discount;
    private String Code;
    private Date expiry;

    private String contact;
    private String location;
    private int maxUses;
    private String PromoMsg;
}
