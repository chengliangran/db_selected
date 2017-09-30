package com.clr.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clr.common.ConnectionManager;
import com.clr.common.JSONMap;
import com.clr.common.JSONParser;
import com.clr.utils.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class TestSpider {

    public static void main(String[] args) {
        ConnectionManager.initConnection();
        try {
//          拿到url
            URL url=new URL("https://api.douban.com/v2/book/1220562");
            URLConnection connection= url.openConnection();
            connection.setRequestProperty("Content-type", "text/html");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");

            connection.connect();
            InputStream inputStream= connection.getInputStream();
            byte[] buf=new byte[4022];
            inputStream.read(buf);
            JSONObject object= JSON.parseObject(new String(buf,0,buf.length).trim());
            Logger.logJSON(object);

//          测试自己写的json类
            JSONParser jsonParser=new JSONParser();
            jsonParser.parseJSONMap(object);
            JSONMap map=jsonParser.getMap();
            String sql="insert into book values("+map.getString("id")+","+map.getString("title")+","+map.getString("author[0]")+","+map.getString("image")+","+map.getString("publisher")+","+map.getString("pubdate")+")";
            System.out.println(sql);
            Map map1= map.getMap();
            for (Object entry: map1.entrySet()) {
                Map.Entry entry1=(Map.Entry) entry;
                System.out.println(entry1.getKey()+"---"+entry1.getValue());
            }
            //            测试数据库
            Record record=Db.findFirst("select * from book");
            System.out.println("--"+record);
            String sql1="insert into book values('"+map.getString("id")+" ','"+map.getString("title")+" ','"+map.getString("author[0]")+" ','"+map.getString("image")+" ','"+map.getString("publisher")+"','"+map.getString("pubdate")+"')";
            System.out.println(sql1);
            Db.update(sql1);
            Object[] paras={};
            Db.update(sql,paras);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
