package com.clr.utils;

import com.clr.common.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class Tester1 {
    public static void main(String[] args) {
        ConnectionManager.initConnection();
        try {
            URL url=new URL("https://api.douban.com/v2/book/1000114");
            try {
                URLConnection connection= url.openConnection();
                connection.connect();
                InputStream inputStream= connection.getInputStream();
                byte[] buf =new byte[2048];
                StringBuffer sb=new StringBuffer();
                while ((inputStream.read(buf))!=-1){
                    System.out.println(new String(buf,0,buf.length));
                    sb.append(new String(buf,0,buf.length));
                }
                System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
