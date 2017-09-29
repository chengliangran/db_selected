package com.clr.spider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class DoubanBookGetter {

    public void queryBooks() {
        //设置地址
        String path = "";
        URL url=null;
        try {
             url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //设置查询的工具
        try {
            URLConnection connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 }
