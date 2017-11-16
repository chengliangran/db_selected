package com.clr.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class StrUtils {


    public static boolean isNull(String str){
        if ("".equals(str)||null==str){
            return true;
        }
        return false;


     }

    public static boolean isNull(String... args){
        boolean flag=false;
        for (int i = 0; i < args.length; i++) {
            if ("".equals(args[i])||null==args[i]){
                flag=true   ;
            }
        }
        return flag;
    }

    public static String subString(String reg,String targetStr){
        Matcher matcher=Pattern.compile(reg).matcher(targetStr);
        if (matcher.find()){
            System.out.println(matcher.group(0));
            return matcher.group(0);
        }else {
            return "";
        }
    }

    public static void main(String[] args) {
//        Pattern p = Pattern.compile("/<span class=\"pl\">页数:</span>[\\u4e00-\\u9fa5\\.a-zA-Z0-9\\s]{0,}</");
//        Matcher m = p.matcher("<span class=\"pl\">作者:</span>&nbsp; \n" +
//                "<a href=\"https://book.douban.com/author/259683/\"> 程婧波</a> \n" +
//                "<br /> \n" +
//                "<span class=\"pl\">出版社:</span> 湖南文艺出版社\n" +
//                "<br /> \n" +
//                "<span class=\"pl\">出版年:</span> 2016-6\n" +
//                "<br /> \n" +
//                "<span class=\"pl\">页数:</span> 224\n" +
//                "<br /> \n" +
//                "<span class=\"pl\">定价:</span> 36.80元\n" +
//                "<br /> \n" +
//                "<span class=\"pl\">装帧:</span> 平装\n" +
//                "<br /> \n" +
//                "<span class=\"pl\">ISBN:</span> 9787540475833\n" +
//                "<br />");
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("cc");
        System.out.println(stringBuilder.toString());
    }
}
