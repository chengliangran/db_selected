package com.clr.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clr.common.JSONMap;
import com.clr.common.JSONParser;
import com.clr.config.SpiderConfig;
import com.clr.utils.Logger;
import com.jfinal.plugin.activerecord.Db;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class DoubanBookAPIer {

    public void queryBooks() {
        //设置地址
        int j=Db.findFirst("select * from book order by id desc").getInt("id");
        for (int i=j+1;i<2000000;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String path = SpiderConfig.DB_BOOK_PREFIX+i;
            URL url=null;
            try {
                 url = new URL(path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //设置查询的工具connection
            URLConnection connection=null;
            try {
                 connection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //---设置参数
            //启动
            try {
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取解析
            try {
                InputStream inputStream=connection.getInputStream();
                StringBuilder sb=new StringBuilder();
                byte[] buf=new byte[2048];
                int index=0;
                while ((index=inputStream.read(buf))!=-1){
                    sb.append(new String(buf,0,index));
                }
                String jsonStr=sb.toString();
                if (jsonStr.contains("average\":\"0.0\"")||!jsonStr.contains("\"isbn13\":\"9787")){
                }
                else {
                    JSONObject object= JSON.parseObject(jsonStr.trim());
                    Logger.logJSON(object);
                    JSONMap map=new JSONParser(object).getMap();
                    for (Map.Entry<String,Object> entry : map.getMap().entrySet()) {
                        System.out.println(entry.getKey()+"---"+entry.getValue());
                    }
                    System.out.println(map.getString("id"));
                    Db.update("insert into book values ("+map.getString("id")+",'"+jsonStr+"')");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

 }
