package com.clr.config;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/11 0011.
 */
public class ProxyConfig {
    public static String XUN_URL="http://www.xdaili.cn/ipagent//freeip/getFreeIps?page=1&rows=10";

    public static String KUAI_URL="http://www.kuaidaili.com/free/";
    public static String KUAI_HOST="//*[@id=\"list\"]/table/tbody/tr[index]/td[1]/allText()";//开头是2
    public static String KUAI_PORT="//*[@id=\"list\"]/table/tbody/tr[index]/td[2]/allText()";

    public static String QUANNET_URL="http://www.goubanjia.com/free/anoy/%E9%AB%98%E5%8C%BF/index.shtml";
    public static String QUANNET_HOSTANDPORT="//*[@id=\"list\"]/table/tbody/tr[index]/td[1]/";

    public static String XICI_URL="http://www.xicidaili.com/nn";
    public static String XICI_HOST="//*[@id=\"ip_list\"]/tbody/tr[index]/td[2]/allText()";//开头是2//*[@id="ip_list"]/tbody/tr[2]/td[3]
    public static String XICI_PORT="//*[@id=\"ip_list\"]/tbody/tr[index]/td[3]/allText()";

    //*[@id="list"]/table/tbody/tr[1]/td[1]    //*[@id="list"]/table/tbody/tr[1]/td[1]/p[1]
    public static String CLOUD_URL="http://www.data5u.com/free/anoy/%E9%AB%98%E5%8C%BF/index.html";
 }
