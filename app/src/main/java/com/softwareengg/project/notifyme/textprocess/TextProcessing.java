package com.softwareengg.project.notifyme.textprocess;

import android.util.Log;

import com.softwareengg.project.notifyme.Promo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

public class TextProcessing {
    private static final String TAG = "NotifyMe";

    //Get the coupon code
    public static String getCode(String[] msg, String[] org){
        for(int i=0;i<msg.length;i++) {
            if(msg[i].equals("code") || msg[i].equals("code:") || msg[i].equals("voucher:")  || msg[i].equals("cpn") || msg[i].equals("cpn:")) {
                return org[i+1];
            }
            if(msg[i].length() > 5){
                if(msg[i].substring(0,5).equals("code:") || msg[i].substring(0,4).equals("cpn:")) {
                    return org[i].substring(5);
                }
            }
        }
        return null;
    }

    //Get the discount as percentage if present, otherwise return 0
    public static int getDiscountPercent(String[] msg){
        for(int i=0;i<msg.length;i++) {
            int index = msg[i].indexOf("%");
            if(index != -1) {
                return Integer.parseInt(msg[i].substring(0,index));
            }
        }
        return 0;
    }

    //Get the discount amount if present, otherwise return 0
    public static int getDiscountAmount(String[] msg){
        String answer = "0";
        for(int i=0;i<msg.length;i++) {
            if(msg[i].equals("upto")) {
                if(msg[i+1].contains("%"))
                    continue;
                else{
                    if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee")) {
                        if(msg[i+2].contains("%"))
                            continue;
                        else
                            answer = msg[i+2];
                    }
                    else
                        answer = msg[i+1];
                }
            }
            if(msg[i].length() > 3) {
                if(msg[i].substring(msg[i].length()-3).equals("off")) {
                    if(msg[i].substring(0,msg[i].length()-3).contains("%"))
                        continue;
                    else
                        answer = msg[i].substring(0,msg[i].length()-3);
                }
            }
            if(msg[i].equals("off") || msg[i].equals("cashback") || msg[i].equals("cashback*")) {
                if(msg[i-1].contains("%"))
                    continue;
                else{
                    if(msg[i-1].equals("rs") || msg[i-1].equals("rupee") || msg[i-1].equals("rupee")) {
                        if(msg[i-2].contains("%"))
                            continue;
                        else
                            answer = msg[i-2];
                    }
                    else
                        answer = msg[i-1];
                }
            }
        }
        for(int i=0;i<msg.length;i++) {
            if(msg[i].equals("flat")) {
                if(msg[i+1].contains("%"))
                    continue;
                else{
                    if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee")) {
                        if(msg[i+2].contains("%"))
                            continue;
                        else
                            answer = msg[i+2];
                    }
                    else
                        answer = msg[i+1];
                }
            }
            if(msg[i].length() >= 3) {
                if( msg[i].substring(msg[i].length()-3).equals("get")) {
                    if(msg[i+1].contains("%"))
                        continue;
                    else{
                        if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee")) {
                            if(msg[i+2].contains("%"))
                                continue;
                            else
                                answer = msg[i+2];
                        }

                    }
                }
            }
        }
        answer = answer.replace("rs", "");
        answer = answer.replace("rupee", "");
        answer = answer.replace("rupees", "");
        int ans;
        try {
            ans = Integer.parseInt(answer);
        }
        catch(Exception ex){
            ans = 0;
        }
        return ans;
    }

    //Check whether the message is a promo of not
    public static boolean isPromo(String[] msg){
        int count = 0;
        for(String s : msg) {
            if(s.contains("t&c") || s.contains("tnc") || s.contains("free") || s.contains("sale") || s.contains("discount") || s.contains("cashback"))
                count += 3;
            if(s.contains("off") || s.contains("flat") || s.contains("buy") || s.contains("voucher") || s.contains("coupon") || s.contains("code") || s.contains("cpn") || s.contains("valid"))
                count += 2;
            if(s.contains("get") || s.contains("limited") || s.contains("period") || s.contains("extra") || s.contains("%") || s.contains("cash") || s.contains("till") || s.contains("rs"))
                count += 1;
        }
        if(count > 4)
            return true;
        else
            return false;
    }

    //Get the expiry date of the promo
    public static Date getValidity(String[] msg){
        Date answer;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for(int i=msg.length-1;i>0;i--) {
            if(msg[i].contains("today") || msg[i].contains("midnight")) {
                answer = new Date(Calendar.getInstance().getTime().getTime());
                return answer;
            }
            if(msg[i].contains("tomorrow")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                answer = new Date(cal.getTime().getTime());
                return answer;
            }
            if(msg[i].equals("jan") || msg[i].equals("january") || msg[i].equals("feb") || msg[i].equals("february") || msg[i].equals("mar") || msg[i].equals("march") || msg[i].equals("apr") || msg[i].equals("april") || msg[i].equals("may") || msg[i].equals("jun") || msg[i].equals("june") || msg[i].equals("jul") || msg[i].equals("july") || msg[i].equals("aug") || msg[i].equals("august") || msg[i].equals("sep") || msg[i].equals("september") || msg[i].equals("oct") || msg[i].equals("october") || msg[i].equals("nov") || msg[i].equals("november") || msg[i].equals("dec") || msg[i].equals("december")) {
                String temp = "";
                if(msg[i-1].length() > 2)
                    temp = temp + msg[i-1].substring(0, msg[i-1].length()-2)+"/";
                else
                    temp = temp+msg[i-1]+"/";
                if(msg[i].equals("jan") || msg[i].equals("january"))
                    temp = temp+"01";
                else if(msg[i].equals("feb") || msg[i].equals("february"))
                    temp = temp+"02";
                else if(msg[i].equals("mar") || msg[i].equals("march"))
                    temp = temp+"03";
                else if(msg[i].equals("apr") || msg[i].equals("april"))
                    temp = temp+"04";
                else if(msg[i].equals("may"))
                    temp = temp+"05";
                else if(msg[i].equals("jun") || msg[i].equals("june"))
                    temp = temp+"06";
                else if(msg[i].equals("jul") || msg[i].equals("july"))
                    temp = temp+"07";
                else if(msg[i].equals("aug") || msg[i].equals("august"))
                    temp = temp+"08";
                else if(msg[i].equals("sep") || msg[i].equals("september"))
                    temp = temp+"09";
                else if(msg[i].equals("oct") || msg[i].equals("october"))
                    temp = temp+"10";
                else if(msg[i].equals("nov") || msg[i].equals("november"))
                    temp = temp+"11";
                else if(msg[i].equals("dec") || msg[i].equals("december"))
                    temp = temp+"12";
                temp = temp + "/"+Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate;
                try {
                    utilDate = df.parse(temp);
                    cal.setTime(utilDate);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    answer = new Date(cal.getTime().getTime());
                    return answer;
                } catch (ParseException ex) {
                    Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    //The maximum number of times the promo can be used
    public static int getMaxUses(String[] msg){
        for(int i=0;i<msg.length;i++) {
            if(msg[i].equals("rides"))
                return Integer.parseInt(msg[i-1]);
        }
        return 1;
    }

    //Get x Buy y kind of offers
    public static int[] getFreebies(String[] msg){
        int answer[] = new int[2];
        boolean get = false;
        boolean buy = false;
        for(int i=0;i<msg.length;i++) {
            if(msg[i].equals("get")){
                if(msg[i+1].matches("[0-9]+")){
                    answer[1] = Integer.parseInt(msg[i+1]);
                    get = true;
                }
            }
            if(msg[i].equals("buy")){
                if(msg[i+1].matches("[0-9]+")){
                    answer[0] = Integer.parseInt(msg[i+1]);
                    buy = true;
                }
            }
        }
        if(buy && get)
            return answer;
        return null;
    }

    //Get the category(food, travel, etc.) to which the promo belongs
    public static String getCategory(String[] msg){
        int food=0,travel=0,cloth=0,accessories=0,entertainment=0;
        for(int i=0;i<msg.length;i++) {
            if(msg[i].contains("pizza") || msg[i].contains("delicious") || msg[i].contains("dominos") || msg[i].contains("food") || msg[i].contains("medium") || msg[i].contains("large"))
                food++;
            else if(msg[i].contains("ride") || msg[i].contains("air") || msg[i].contains("travel") || msg[i].contains("trip") || msg[i].contains("uber") || msg[i].contains("ola") || msg[i].contains("fly"))
                travel++;
            else if(msg[i].contains("clothing") || msg[i].contains("wardrobe"))
                cloth = cloth+2;
            else if(msg[i].contains("jeans") || msg[i].contains("shirt") || msg[i].contains("trouser") || msg[i].contains("suit") || msg[i].contains("store") || msg[i].contains("merchandi"))
                cloth++;
            else if(msg[i].contains("accessor"))
                accessories = accessories+2;
            else if(msg[i].contains("lens") || msg[i].contains("glasses") || msg[i].contains("merchandi") || msg[i].contains("store"))
                accessories++;
            else if(msg[i].contains("entertainment") || msg[i].contains("ticket") || msg[i].contains("book") || msg[i].contains("movie") || msg[i].contains("show"))
                entertainment++;
        }
        int maxima = Math.max(food,Math.max(Math.max(travel,cloth),Math.max(accessories,entertainment)));

        if(maxima < 2)
            return "misc";
        if(food == maxima)
            return "food";
        if(travel == maxima)
            return "travel";
        if(cloth == maxima)
            return "clothing";
        if(accessories == maxima)
            return "accessories";
        if(entertainment == maxima)
            return "movies";
        return "misc";
    }

    //Give a score to the promo according to a score function
    public static int getScore(String[] msg,int wtDiscountP,int wtDiscountA,int wtUses, int wtValidity){
        int discountP = getDiscountPercent(msg);
        int discountA = getDiscountAmount(msg);
        int maxUses = getMaxUses(msg);
        Date validity = getValidity(msg);
        int score = 0;
        if(validity!=null){
            Date today = new Date(Calendar.getInstance().getTime().getTime());
            if(validity==today){
                score+=10*wtValidity;
            }else{
                int temp = (10 - ((int) ((validity.getTime() - today.getTime()) / (1000 * 60 * 60 * 24))));
                if(temp<0) temp=0;
                score+= temp*wtValidity;
            }
        }
        score+=discountP*wtDiscountP;
        score+=discountA*wtDiscountA;
        score+=getMaxUses(msg)*wtUses;
        return score;
    }

    //Get the vendor(Dominos, Uber, etc.) of the promo
    public static String getVendor(String[] promoMsgParts, String[] vendors) {
        for(String part : promoMsgParts) {
            for(String vendor : vendors) {
                if(part.contains(vendor.toLowerCase())) {
                    return vendor;
                }
            }
        }
        return "Others";
    }

    //Parse the promo message to obtain all the relevant information about the promo
    public static Promo parsePromo(String promoMsg, String[] vendors) {
        Promo promo = new Promo();
        String[] promoMsgParts = promoMsg.replaceAll("[.,!\n]", " ").toLowerCase().split(" +");
        String category, vendor;
        category = getCategory(promoMsgParts);
        vendor = getVendor(promoMsgParts, vendors);
        if(!vendor.equals("Others") || !category.equals("misc") || isPromo(promoMsgParts)) {
            promo.setCategory(category);
            promo.setVendor(vendor);
        } else {
            return null;
        }
        promo.setDiscountAmount(getDiscountAmount(promoMsgParts));
        promo.setDiscountPercentage(getDiscountPercent(promoMsgParts));
        promo.setCode(getCode(promoMsgParts,promoMsg.replaceAll("[.,!\n]", " ").toLowerCase().split(" +")));
        promo.setExpiry(getValidity(promoMsgParts));
        promo.setMaxUses(getMaxUses(promoMsgParts));
        promo.setPromoMsg(promoMsg);
        promo.setScore(getScore(promoMsgParts,1,1,1,1));
        return promo;
    }
}
