package com.softwareengg.project.notifyme;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by shivanshu on 15/04/17.
 */

public class Filter {
    String category;
    ArrayList<String> vendors;
    String receipt;
    String expiry;

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
