package com.clr.spider.api;

import com.alibaba.fastjson.JSON;
import com.clr.utils.JSONMap;
import com.clr.utils.JSONObject1;
import com.clr.utils.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class BasicAPIer{

    public void queryByAPI(String path,String sql,float interval) {
        //设置地址
        int lastId=1000000;
        Record r= Db.findFirst(sql);
        if (r!=null){
            lastId=r.getInt("id");
        }
        for (int i=lastId+1;i<2000000;i++) {
            //设置间隔
            queryInterval(interval);
            String complteddPath = path+i;
            URL url=null;
            try {
                url = new URL(complteddPath);
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
                    com.alibaba.fastjson.JSONObject object= JSON.parseObject(jsonStr.trim());
                    Logger.logJSON(object);
                    JSONMap map=new JSONObject1(object).getMap();
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

    //
    private void queryInterval(float interval) {
        float per=60/interval;
        int perTime= (int) (per*1000);
        try {
            Thread.sleep(perTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
