package com.clr.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.clr.common.MyConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class TestSpider {

    public static void main(String[] args) {
        MyConnection.initConnection();
        try {
            URL url=new URL("https://api.douban.com/v2/book/1220562");
            URLConnection connection= url.openConnection();
            connection.connect();
            InputStream inputStream= connection.getInputStream();
            byte[] buf=new byte[4022];
            inputStream.read(buf);
            System.out.println(new String(buf,0,buf.length));
            JSONObject object= JSON.parseObject(new String(buf,0,buf.length).trim());
            JSONObject ratting= object.getJSONObject("rating");
            System.out.println(ratting.getString("average"));

         } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
