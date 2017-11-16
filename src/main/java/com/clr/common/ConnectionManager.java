package com.clr.common;

import com.clr.config.Config;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class ConnectionManager {
    public static DruidPlugin initConnection(){
        System.out.println("####启动数据库");
        DruidPlugin dp=new DruidPlugin(Config.HOST,Config.USER,Config.PASSWORD, Config.driverClass);
        dp.start();
        ActiveRecordPlugin arp=new ActiveRecordPlugin(dp);
        arp.start();
        System.out.println();
        return dp;
     }

    public static void main(String[] args) {
        initConnection();
        Record record= Db.findFirst("select * from book");
        System.out.println(record);
    }
}
