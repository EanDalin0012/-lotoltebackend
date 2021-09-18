package com.loto.lte.core.common;

import com.loto.lte.core.util.CurrentDateUtil;

import java.text.SimpleDateFormat;

public class TxrIDKeyGenerator {

    public static  String generate(String prefix) {
        return new StringBuffer()
                .append(prefix)
                .append("-")
                .append(get())
                .toString();
    }

    private static String get() {
        try {
            String pattern = "MMyyyydd-hhmmss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(CurrentDateUtil.getLocalDateTime());
        }catch (Exception e) {

        }
        return null;
    }
}
