package com.softwareengg.project.notifyme.textprocess;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by startup on 25-04-2017.
 */
public class TextProcessingTest {
    public static String promo1 = "Dear Rider, POOL is at flat Rs49 in NCR only till tomorrow 9 Feb! Valid on trips within Gurgaon, Delhi, Noida-Ghaziabad & Faridabad, upto 8km. t.uber.com/49ncr";
    public static String promo2 = "Delicious foodpanda offer - 40% off on your first order. Use code NEWPANDA. Pay via wallets & 15% cash back too. Order Now: https://chk.bz/9k8pe69wzb";
    public static String promo3 = "Dear Rider, use code DELWEEK & get Rs. 50 off 2 rides on uberGO or uberX. Valid only for you till midnight of Wed. 25 Jan, in Delhi NCR. Uber on!";
    public static String promo4 = "Don't miss this! Use code JAN50 before 24 Jan & get Rs.50 cashback on Rs.50 transaction for deepanker27mishra@gmail.com. T&C Apply: http://frch.in/kk";
    public static String promo5 = "Dominos Super Value Offer Only For You;Buy 1 Medium/Large Pizza &Get 30% OFF.WalkIn/Order@ 68886888/ goo.gl/CQThqp Cpn: CRMF182F4EC80 Valid till 08 Jan T&C";
    public static String nonpromo = "Hello, let's meet today at 4:30 pm. Please don't be late.";

    public static String promo7 = "Don’t let Cockroaches hide in your home during Winters. Pest Control by HICARE with FLAT 40% Off on yearly Cockroach Service.T&C*.For details, sms PEST to 56161";

    String str1 = promo1.replaceAll("[.,!\n]", " ");
    String lowerstr1 = str1.toLowerCase();
    String[] msg1 = lowerstr1.split(" +");
    String[] org1 = str1.split(" +");

    String str2 = promo2.replaceAll("[.,!\n]", " ");
    String lowerstr2 = str2.toLowerCase();
    String[] msg2 = lowerstr2.split(" +");
    String[] org2 = str2.split(" +");

    String str3 = promo3.replaceAll("[.,!\n]", " ");
    String lowerstr3 = str3.toLowerCase();
    String[] msg3 = lowerstr3.split(" +");
    String[] org3 = str3.split(" +");

    String str4 = promo4.replaceAll("[.,!\n]", " ");
    String lowerstr4 = str4.toLowerCase();
    String[] msg4 = lowerstr4.split(" +");
    String[] org4 = str4.split(" +");

    String str5 = promo5.replaceAll("[.,!\n]", " ");
    String lowerstr5 = str5.toLowerCase();
    String[] msg5 = lowerstr5.split(" +");
    String[] org5 = str5.split(" +");

    String str6 = nonpromo.replaceAll("[.,!\n]", " ");
    String lowerstr6 = str6.toLowerCase();
    String[] msg6 = lowerstr6.split(" +");

    String str7 = promo7.replaceAll("[.,!\n]", " ");
    String lowerstr7 = str7.toLowerCase();
    String[] msg7 = lowerstr7.split(" +");
    String[] org7 = str7.split(" +");

    @Test
    public void getCode() throws Exception {
        String exp2 = "NEWPANDA";
        String result2 = TextProcessing.getCode(msg2,org2);
        assertEquals(exp2, result2);

        String exp3 = "DELWEEK";
        String result3 = TextProcessing.getCode(msg3,org3);
        assertEquals(exp3, result3);

        String exp4 = "JAN50";
        String result4 = TextProcessing.getCode(msg4,org4);
        assertEquals(exp4, result4);

        String exp5 = "CRMF182F4EC80";
        String result5 = TextProcessing.getCode(msg5,org5);
        assertEquals(exp5, result5);

        String exp7 = null;
        String result7 = TextProcessing.getCode(msg7,org7);
        assertEquals(exp7, result7);
    }

    @Test
    public void getDiscountPercent() throws Exception {
        int exp2 = 40;
        int result2 = TextProcessing.getDiscountPercent(msg2);
        assertEquals(exp2, result2);

        int exp5 = 30;
        int result5 = TextProcessing.getDiscountPercent(msg5);
        assertEquals(exp5, result5);

        int exp7 = 40;
        int result7 = TextProcessing.getDiscountPercent(msg7);
        assertEquals(exp7, result7);
    }

    @Test
    public void getDiscountAmount() throws Exception {
        int exp1 = 49;
        int result1 = TextProcessing.getDiscountAmount(msg1);
        assertEquals(exp1, result1);

        int exp3 = 50;
        int result3 = TextProcessing.getDiscountAmount(msg3);
        assertEquals(exp3, result3);

        int exp4 = 50;
        int result4 = TextProcessing.getDiscountAmount(msg4);
        assertEquals(exp4, result4);

        int exp7 = 0;
        int result7 = TextProcessing.getDiscountAmount(msg7);
        assertEquals(exp7, result7);
    }


    @Test
    public void getValidity() throws Exception {
        try {
            System.out.println("getValidity");
            String[] msg = null;
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Calendar cal = Calendar.getInstance();
            java.util.Date utilDate;
            utilDate = df.parse("09-02-2017"); // your util date
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date exp1 = new Date(cal.getTime().getTime());
            Date result1 = TextProcessing.getValidity(msg1);
            assertEquals(exp1, result1);

            utilDate = df.parse("25-01-2017"); // your util date
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date exp3 = new Date(cal.getTime().getTime());
            Date result3 = TextProcessing.getValidity(msg3);
            assertEquals(exp3, result3);

            utilDate = df.parse("08-01-2017"); // your util date
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date exp5 = new Date(cal.getTime().getTime());
            Date result5 = TextProcessing.getValidity(msg5);
            assertEquals(exp5, result5);


            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
        } catch (ParseException ex) {
        }
    }

    @Test
    public void getMaxUses() throws Exception {
        int exp3 = 2;
        int result3 = TextProcessing.getMaxUses(msg3);
        assertEquals(exp3, result3);
    }

    @Test
    public void isPromo() throws Exception{
        boolean exp1 = true;
        boolean result1 = TextProcessing.isPromo(msg1);
        assertEquals(exp1, result1);

        boolean exp2 = true;
        boolean result2 = TextProcessing.isPromo(msg2);
        assertEquals(exp2, result2);

        boolean exp3 = true;
        boolean result3 = TextProcessing.isPromo(msg3);
        assertEquals(exp3, result3);

        boolean exp4 = true;
        boolean result4 = TextProcessing.isPromo(msg4);
        assertEquals(exp4, result4);

        boolean exp5 = true;
        boolean result5 = TextProcessing.isPromo(msg5);
        assertEquals(exp5, result5);

        boolean exp6 = false;
        boolean result6 = TextProcessing.isPromo(msg6);
        assertEquals(exp6, result6);

        boolean exp7 = true;
        boolean result7 = TextProcessing.isPromo(msg7);
        assertEquals(exp7, result7);
    }

    @Test
    public void getCategory() throws Exception {
        String exp1 = "travel";
        String result1 = TextProcessing.getCategory(msg1);
        assertEquals(exp1, result1);

        String exp2 = "food";
        String result2 = TextProcessing.getCategory(msg2);
        assertEquals(exp2, result2);

        String exp3 = "travel";
        String result3 = TextProcessing.getCategory(msg3);
        assertEquals(exp3, result3);

        String exp4 = "misc";
        String result4 = TextProcessing.getCategory(msg4);
        assertEquals(exp4, result4);

        String exp5 = "food";
        String result5 = TextProcessing.getCategory(msg5);
        assertEquals(exp5, result5);

        String exp7 = "misc";
        String result7 = TextProcessing.getCategory(msg7);
        assertEquals(exp7, result7);
    }

}