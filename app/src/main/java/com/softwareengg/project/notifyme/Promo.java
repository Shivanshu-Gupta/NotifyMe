package com.softwareengg.project.notifyme;

import java.sql.Date;
//promo object that represents a promo message
public class Promo {
    private static final String TAG = "NotifyMe";
    
    private String category;
    private String vendor;
    private String Code;
    private double discountAmount;
    private double discountPercentage;
    private int maxUses;
    private double score;
    private Date receipt;
    private Date expiry;
    private String PromoMsg;

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

    public Date getReceipt() {
        return receipt;
    }

    public void setReceipt(Date receipt) {
        this.receipt = receipt;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discount) {
        this.discountAmount = discount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discount) {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    public String getPromoMsg() {
        return PromoMsg;
    }

    public void setPromoMsg(String promoMsg) {
        PromoMsg = promoMsg;
    }
}
