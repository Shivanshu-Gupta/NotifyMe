package com.softwareengg.project.notifyme;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by shivanshu on 15/04/17.
 */

public class Filter {
    String category;
    ArrayList<String> vendors;
    Date receipt;
    Date expiry;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVendors(ArrayList<String> vendors) {
        this.vendors = vendors;
    }

    public void setReceipt(Date receipt) {
        this.receipt = receipt;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getVendors() {
        return vendors;
    }

    public Date getReceipt() {
        return receipt;
    }

    public Date getExpiry() {
        return expiry;
    }
}
