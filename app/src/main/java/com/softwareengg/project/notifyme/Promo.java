package com.softwareengg.project.notifyme;

import java.sql.Date;

;

public class Promo {
    private static final String TAG = "NotifyMe";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discount) {
        this.discountAmount = discount;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discount) {
        this.discountPercentage = discount;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public String getPromoMsg() {
        return PromoMsg;
    }

    public void setPromoMsg(String promoMsg) {
        PromoMsg = promoMsg;
    }

    private String category;
    private String vendor;
    private Date receivedOn;

    private float discountAmount;
    private float discountPercentage;
    private String Code;
    private Date expiry;

    private int maxUses;
    private String PromoMsg;
}
