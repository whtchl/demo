package com.bluetron.utils;

import java.util.Date;

public class TUtils {
    public static String longtoDateString(long dateTime){
        //long dateTime = 14830682769461;
        Date date = new Date(dateTime);
        //System.out.println(date.toString());
        return  date.toString();
    }
}
