package com.loto.lte.core.util;

public class ThirdPartyID {
    public static String generate(int accountId, String date) {
//        20210911 04:47:17
        String dateArr[];
        if (date != null ) {
            dateArr = date.split(" ");
            String yy1 = dateArr[0].substring(0, 2);
            String yy2 = dateArr[0].substring(2,2);
            String mm = dateArr[0].substring(4,2);
            String dd = dateArr[0].substring(6,4);
            String ss = "";
            String hh = "";
            System.out.println("yy1:"+yy1);
            System.out.println("yy2:"+yy2);
            System.out.println("mm:"+mm);
            System.out.println("dd:"+dd);
            if (dateArr[1] != null) {
                String timeArrArr[] = dateArr[1].split(":");
                hh = timeArrArr[0];
                ss = timeArrArr[1];
            } else {
                
            }

            System.out.println("hh:"+hh);
            System.out.println("ss:"+ss);


        }


     return "0";
    }
}
