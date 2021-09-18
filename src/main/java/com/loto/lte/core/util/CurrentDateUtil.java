package com.loto.lte.core.util;

import com.loto.lte.core.common.TxrIDKeyGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentDateUtil {
    public static Date getLocalDateTime() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        return localDateFormat.parse( simpleDateFormat.format(new Date()) );
    }

    public static Date getCurrentUtcTime() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        return localDateFormat.parse( simpleDateFormat.format(new Date()) );
    }


    public static String get() throws ParseException {
        String pattern = "yyyyMMdd hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(CurrentDateUtil.getLocalDateTime());
    }

    public static void main(String a[]) throws Exception {

        String pattern = "yyyyMMdd hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(CurrentDateUtil.getLocalDateTime());

        System.out.println(date);
        System.out.println(CurrentDateUtil.getCurrentUtcTime());

        System.out.println("TxrIDKeyGenerator:"+TxrIDKeyGenerator.generate("Dsp"));
    }
}
