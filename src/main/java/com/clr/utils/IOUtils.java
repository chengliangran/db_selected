package com.clr.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/10/19 0019.
 */
public class IOUtils {
    public static String convertStreamToString(InputStream is) {
        if (is == null)
            return "";
        byte[] buf=new byte[2048];
        StringBuffer sb=new StringBuffer();
        int index=0;
        try {
            index=is.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        while (index!=-1){
            sb.append(new String(buf,0,index));
            try {
                index=is.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return sb.toString();
    }
}
