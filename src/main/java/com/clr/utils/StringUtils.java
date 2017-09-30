package com.clr.utils;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class StringUtils {

    public static boolean isNull(String str){
        if ("".equals(str)||null==str){
            return true;
        }
        return false;
    }

}
