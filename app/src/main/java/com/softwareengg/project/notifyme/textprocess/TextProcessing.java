package com.softwareengg.project.notifyme.textprocess;

import com.softwareengg.project.notifyme.Promo;

/**
 * Created by shivanshu on 20/02/17.
 */

public interface TextProcessing {
    //public String getCode(String[] msg, String[] org);
    public Promo parsePromo(String PromoMsg);
}
