package com.clr.common;

import com.alibaba.fastjson.JSON;
import com.clr.utils.IOUtils;
import com.clr.utils.StrUtils;
import com.clr.config.ProxyConfig;
import com.clr.utils.JSONObject1;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

/**
 * Created by Administrator on 2017/10/11 0011.
 */
public class ProxyManager implements Runnable{

    public static boolean stopped=false;

    public static List<String> proxyList=new ArrayList<String>();

    public static List<String> getProxyList(){
        return proxyList;
    }

    //开启多线程
    public void run() {
        startServer();
    }

    public static void start(){
        Thread thread=new Thread(new ProxyManager());
        thread.start();
    }

    public synchronized static String[] getHostAndPort(){
        System.out.println(123);
        while (true) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (proxyList.size()>0){//遍历ip测试代理ip是否可用，如果可用，则创建爬虫
                String str = null;
                try {
                    str = proxyList.remove(0);
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
                String[] hostAndPort=str.split("-");
                String host1 = hostAndPort[0];
                String port1 = hostAndPort[1];
                if (!StrUtils.isNull(host1,port1)&&test(host1,port1)){
//                        proxyList.add(host1+"-"+port1);
                        return new String[]{host1,port1};
                }
            }
        }
    }

    //启动服务---
    private void startServer(){
        while (true){
            //代理网站的url和connection
            String page=null;
            try {
                //1 拿到寻代理的ips
                page=getRemotePage(ProxyConfig.XUN_URL,false);
                if (page!=null) {
                    for (int j = 0; j < 10; j++) {
                        String host=new JSONObject1(JSON.parseObject(page)).path("RESULT/rows["+j+"]/ip");
                        String port=new JSONObject1(JSON.parseObject(page)).path("RESULT/rows["+j+"]/port");
                        if (!StrUtils.isNull(host,port)){
                            proxyList.add(host+"-"+port);
                        }
                    }
                }
                //2 拿到快代理的ips
                page=getRemotePage(ProxyConfig.KUAI_URL,false);
                if (page!=null) {
                    for (int i = 1; i < 16; i++) {
                        Html pageHtml= new Html(page);
                        String host=pageHtml.xpath(ProxyConfig.KUAI_HOST.replace("index",i+"")).toString();
                        String port=pageHtml.xpath(ProxyConfig.KUAI_PORT.replace("index",i+"")).toString();
                        if (!StrUtils.isNull(host,port)){
                            proxyList.add(host+"-"+port);
                        }
                    }
                }
                //拿到西刺代理
//                String[] userAgent={"User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36"};
//                page=getRemotePage(ProxyConfig.XICI_URL,true);
//                Html pageHtml= new Html(page);
//                for (int i = 2; i < 100; i++) {
//                    String host=pageHtml.xpath(ProxyConfig.XICI_HOST.replace("index",i+"")).toString();
//                    String port=pageHtml.xpath(ProxyConfig.XICI_PORT.replace("index",i+"")).toString();
//
//                    if (!StrUtils.isNull(host,port)){
//                        proxyList.add(host+"-"+port);
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //间隔
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRemotePage(String urlStr,boolean needAgent){
        URL url=null;
        URLConnection connection=null;
        InputStream inputStream=null;
        String page=null;
        try {
            url=new URL(urlStr);
            connection=url.openConnection();
            if (needAgent) {
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            }
            connection.connect();
            inputStream= connection.getInputStream();
            return IOUtils.convertStreamToString(inputStream);
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }
    //时间间隔
    private void threadSleep(int milles){
        try {
            Thread.sleep(milles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean test(String host,String port){
        try {
            URL url = null;
            try {
                url = new URL("http://www.baidu.com");
            } catch (MalformedURLException e) {
//                System.out.println("url invalidate");
            }
            InetSocketAddress addr = null;
            addr = new InetSocketAddress(host, Integer.parseInt(port));
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr); // http proxy
            InputStream in = null;
            try {
                URLConnection conn = url.openConnection(proxy);
                conn.setConnectTimeout(1000);
                conn.setReadTimeout(2000);
                in = conn.getInputStream();
            } catch (Exception e) {
//                System.out.println("ip " + host + " is not aviable");//异常IP
            }
            String s = IOUtils.convertStreamToString(in);
            if (s.indexOf("baidu") > 0) {//有效IP
                return true;
            }else {
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
     }

    public static void main(String[] args) {
        ProxyManager.start();
        while (true){
            System.out.println(ProxyManager.getHostAndPort());
        }
     }
}
