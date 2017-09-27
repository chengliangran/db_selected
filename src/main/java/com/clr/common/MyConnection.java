package com.clr.common;

import com.clr.Config;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class MyConnection {
    public static void initConnection(){
        DruidPlugin dp=new DruidPlugin(Config.HOST,Config.USER,Config.PASSWORD);
        new ActiveRecordPlugin(dp).start();
    }
}
