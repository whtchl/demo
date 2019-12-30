package com.bluetron.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TUtils {
    public static String longtoDateString(long dateTime) {
    /*long longTime = new Date().getTime();
    System.out.println("字符串类型的Long日期转换成日期:");
    String str = "1498457677473";
    Long dateLong = Long.valueOf(str);
    System.out.println("longToDate："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateTime)));*/

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateTime)).toString();
    }

    public static String asciiToString(String value) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] chars = value.split(" ");
        for (String c : chars) {
            stringBuffer.append((char) Integer.parseInt(c));
        }
        return stringBuffer.toString();
    }

    public static void ASCIIToConvert() {

        String value = "49 57 51 53 50 49 46 54 52 49 ";

        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(" ");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        System.out.println(sbu.toString());

    }
}
