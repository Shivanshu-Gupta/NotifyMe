package com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by shivanshu on 15/04/17.
 */

public class Filter implements Serializable {
    String category;
    ArrayList<String> vendors;
    String receipt;
    String expiry;

    public Filter() {

    }

    public Filter(String category, ArrayList<String> vendors, String receipt, String expiry) {
        this.category = category;
        this.vendors = vendors;
        this.receipt = receipt;
        this.expiry = expiry;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVendors(ArrayList<String> vendors) {
        this.vendors = vendors;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getVendors() {
        return vendors;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getExpiry() {
        return expiry;
    }
}
