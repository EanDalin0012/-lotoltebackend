package com.loto.lte.core.util;

public class GenerateUtil {
    public static String generate(String prefix, int size, String currentValue, String body, int digitFormat, String format) {
        int length = String.valueOf(currentValue ).length();
        String valueTmr = prefix;
        for (int i = 0 ; i < size - length; i++) {
            valueTmr += body;
        }
        String digitFomatTmr = "";
        System.out.println("a:"+(valueTmr.length()  - digitFormat + 1)+", l:"+valueTmr.length());

        for (int i = 0 ; i < valueTmr.length() - 3; i = i+  digitFormat) {
            System.out.println("i:"+i);
            System.out.println(valueTmr.substring(i, 3));
        }
        String realValue = valueTmr + currentValue;

        return realValue;
    }
}
