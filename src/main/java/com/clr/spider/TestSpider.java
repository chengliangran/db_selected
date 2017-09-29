package com.clr.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clr.common.ConnectionManager;
import com.clr.common.JSONMap;
import com.clr.common.JSONParser;
import com.clr.common.Logger;

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
            URL url=new URL("https://api.douban.com/v2/book/1220562");
            URLConnection connection= url.openConnection();
            connection.connect();
            InputStream inputStream= connection.getInputStream();
            byte[] buf=new byte[4022];
            inputStream.read(buf);
            JSONObject object= JSON.parseObject(new String(buf,0,buf.length).trim());
            Logger.print(object);
//            测试自己写的json类
            JSONParser jsonParser=new JSONParser();
            System.out.println();
            jsonParser.parseJSONMap(object);
            JSONMap map=jsonParser.getMap();
            System.out.println(map.getInt("tags[0]-count"));
            Map<String,Object> map1=map.getMap();
            for (Map.Entry e : map1.entrySet()) {
                System.out.println(e.getKey()+"---"+e.getValue());

            }

         } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
