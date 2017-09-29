package com.clr.common;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class ConnectionManager {
    public static void initConnection(){
        DruidPlugin dp=new DruidPlugin(Config.HOST,Config.USER,Config.PASSWORD, Config.driverClass);
        dp.start();
        ActiveRecordPlugin arp=new ActiveRecordPlugin(dp);
        arp.start();
        System.out.println();

     }

    public static void main(String[] args) {
        initConnection();
        Record record= Db.findFirst("select * from book");
        System.out.println(record);
    }
}
