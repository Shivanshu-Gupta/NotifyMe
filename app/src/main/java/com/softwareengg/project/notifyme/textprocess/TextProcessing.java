package com.softwareengg.project.notifyme.textprocess;

import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.PromoCategory;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shivanshu on 20/02/17.
 */

public class TextProcessing {
    //public String getCode(String[] msg, String[] org);
    private static final String TAG = "NotifyMe";


    public static String getCode(String[] msg, String[] org){
        for(int i=0;i<msg.length;i++)
        {
            if(msg[i].equals("code") || msg[i].equals("code:") || msg[i].equals("voucher:")  || msg[i].equals("cpn") || msg[i].equals("cpn:"))
            {
                return org[i+1];
            }
            if(msg[i].length() > 5){
                if(msg[i].substring(0,5).equals("code:") || msg[i].substring(0,4).equals("cpn:"))
                {
                    return org[i].substring(5);
                }
            }
        }
        return null;
    }

    public static int getDiscountPercent(String[] msg){
        for(int i=0;i<msg.length;i++)
        {
            int index = msg[i].indexOf("%");
            if(index != -1)
            {
                return Integer.parseInt(msg[i].substring(0,index));
            }
        }
        return 0;
    }

    public static int getDiscountAmount(String[] msg){
        String answer = "0";
        for(int i=0;i<msg.length;i++)
        {
            if(msg[i].equals("upto"))
            {
                if(msg[i+1].contains("%"))
                    continue;
                else{
                    if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee"))
                    {
                        if(msg[i+2].contains("%"))
                            continue;
                        else
                            answer = msg[i+2];
                    }
                    else
                        answer = msg[i+1];
                }
            }
            if(msg[i].length() > 3)
            {
                if(msg[i].substring(msg[i].length()-3).equals("off"))
                {
                    if(msg[i].substring(0,msg[i].length()-3).contains("%"))
                        continue;
                    else
                        answer = msg[i].substring(0,msg[i].length()-3);
                }
            }
            if(msg[i].equals("off") || msg[i].equals("cashback") || msg[i].equals("cashback*"))
            {
                if(msg[i-1].contains("%"))
                    continue;
                else{
                    if(msg[i-1].equals("rs") || msg[i-1].equals("rupee") || msg[i-1].equals("rupee"))
                    {
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
        for(int i=0;i<msg.length;i++)
        {
            if(msg[i].equals("flat"))
            {
                if(msg[i+1].contains("%"))
                    continue;
                else{
                    if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee"))
                    {
                        if(msg[i+2].contains("%"))
                            continue;
                        else
                            answer = msg[i+2];
                    }
                    else
                        answer = msg[i+1];
                }
            }
            if(msg[i].length() >= 3)
            {
                if( msg[i].substring(msg[i].length()-3).equals("get"))
                {
                    if(msg[i+1].contains("%"))
                        continue;
                    else{
                        if(msg[i+1].equals("rs") || msg[i+1].equals("rupee") || msg[i+1].equals("rupee"))
                        {
                            if(msg[i+2].contains("%"))
                                continue;
                            else
                                answer = msg[i+2];
                        }
                        else
                            answer = msg[i+1];
                    }
                }
            }
        }
        answer = answer.replace("rs", "");
        answer = answer.replace("rupee", "");
        answer = answer.replace("rupees", "");
        return Integer.parseInt(answer);
    }

    public static boolean isPromo(String[] msg){
        int count = 0;
        for(String s : msg)
        {
            if(s.contains("t&c") || s.contains("tnc") || s.contains("free") || s.contains("sale") || s.contains("discount") || s.contains("cashback"))
                count += 3;
            if(s.contains("off") || s.contains("flat") || s.contains("buy") || s.contains("voucher") || s.contains("coupon") || s.contains("code") || s.contains("cpn"))
                count += 2;
            if(s.contains("get") || s.contains("limited") || s.contains("period") || s.contains("extra") || s.contains("%") || s.contains("cash"))
                count += 1;
        }
        if(count > 5)
            return true;
        else
            return false;
    }

    public static Date getValidity(String[] msg){
        Date answer;
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        for(int i=msg.length-1;i>0;i--)
        {
            if(msg[i].contains("today") || msg[i].contains("midnight"))
            {
                answer = new Date(Calendar.getInstance().getTime().getTime());
                return answer;
            }
            if(msg[i].contains("tomorrow"))
            {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                answer = new Date(cal.getTime().getTime());
                return answer;
            }
            if(msg[i].equals("jan") || msg[i].equals("january") || msg[i].equals("feb") || msg[i].equals("february") || msg[i].equals("mar") || msg[i].equals("march") || msg[i].equals("apr") || msg[i].equals("april") || msg[i].equals("may") || msg[i].equals("jun") || msg[i].equals("june") || msg[i].equals("jul") || msg[i].equals("july") || msg[i].equals("aug") || msg[i].equals("august") || msg[i].equals("sep") || msg[i].equals("september") || msg[i].equals("oct") || msg[i].equals("october") || msg[i].equals("nov") || msg[i].equals("november") || msg[i].equals("dec") || msg[i].equals("december"))
            {
                String temp = "";
                if(msg[i-1].length() > 2)
                    temp = temp + msg[i-1].substring(0, msg[i-1].length()-2)+"-";
                else
                    temp = temp+msg[i-1]+"-";
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
                temp = temp + "-"+Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate;
                try {
                    utilDate = df.parse(temp); // your util date
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

    public static int getMaxUses(String[] msg){
        for(int i=0;i<msg.length;i++)
        {
            if(msg[i].equals("rides"))
                return Integer.parseInt(msg[i-1]);
        }
        return 1;
    }

    public static int[] getFreebies(String[] msg){
        int answer[] = new int[2];
        boolean get = false;
        boolean buy = false;
        for(int i=0;i<msg.length;i++)
        {
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

    public static String getCategory(String[] msg){
        int food=0,travel=0,cloth=0,accessories=0,entertainment=0;
        for(int i=0;i<msg.length;i++)
        {
            if(msg[i].contains("pizza") || msg[i].contains("delicious") || msg[i].contains("dominos") || msg[i].contains("food") || msg[i].contains("medium") || msg[i].contains("large"))
                food++;
            else if(msg[i].contains("ride") || msg[i].contains("air") || msg[i].contains("travel") || msg[i].contains("trip") || msg[i].contains("uber") || msg[i].contains("ola") || msg[i].contains("fly"))
                travel++;
            else if(msg[i].contains("wardrobe") || msg[i].contains("jeans") || msg[i].contains("cloth") || msg[i].contains("shirt") || msg[i].contains("trouser") || msg[i].contains("suit") || msg[i].contains("store"))
                cloth++;
            else if(msg[i].contains("accessor") || msg[i].contains("lens") || msg[i].contains("glasses") || msg[i].contains("merchandi") || msg[i].contains("store"))
                accessories++;
            else if(msg[i].contains("entertainment") || msg[i].contains("ticket") || msg[i].contains("book") || msg[i].contains("movie") || msg[i].contains("show"))
                entertainment++;
        }
        if(food > 1)
            return "food";
        if(travel > 1)
            return "travel";
        if(cloth > 1)
            return "clothing";
        if(accessories > 1)
            return "accessories";
        if(entertainment > 1)
            return "entertainment";
        return "misc";
    }

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

    public Promo parsePromo(String[] promoMsg,String orgMsg) {
        Promo promo = new Promo();
        String category = getCategory(promoMsg);
        switch (category){
            case "food" :
                promo.setCategory(PromoCategory.FOOD);
                break;
            case "travel" :
                promo.setCategory(PromoCategory.TRAVEL);
                break;
            case "clothing" :
                promo.setCategory(PromoCategory.CLOTHING);
                break;
            case "accessories" :
                promo.setCategory(PromoCategory.ACCESSORIES);
                break;
            case "entertainment" :
                promo.setCategory(PromoCategory.ENTERTAINMENT);
                break;
            case "misc" :
                promo.setCategory(PromoCategory.MISC);
                break;
        }
        promo.setDiscountAmount(getDiscountAmount(promoMsg));
        promo.setDiscountPercentage(getDiscountPercent(promoMsg));
        promo.setCode(getCode(promoMsg,orgMsg.replaceAll("[.,!\n]", " ").toLowerCase().split(" +")));
        promo.setExpiry(getValidity(promoMsg));
        promo.setMaxUses(getMaxUses(promoMsg));
        promo.setPromoMsg(orgMsg);
        return promo;
    }
}
