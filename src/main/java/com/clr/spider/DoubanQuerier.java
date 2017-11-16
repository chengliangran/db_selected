package com.clr.spider;

import com.clr.common.ProxyManager;
import com.clr.config.SpiderConfig;
import com.clr.spider.processor.BookPageProcessor;
import com.clr.spider.subject.BookSpider;
import com.clr.utils.Logger;
import com.clr.utils.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class DoubanQuerier {


    public DoubanQuerier(){
    }

    public static void main(String[] args) {
        new DoubanQuerier().queryBook();
    }

    //查询豆瓣书籍
    public void queryBook(){
        System.out.println("####开始对豆瓣图书的tags爬取");
        while (true){
            List<Record> tags= Db.find("select * from tag where pages<>-1 and type='图书'");
            Logger.logLocalText("开始新的一轮爬取，爬虫的总数为"+ tags.size(),false);
            Logger.logLocalText1("开始新的一轮爬取，爬虫的总数为"+ tags.size(),false);

            Logger.logLocalText2("开始整理tag"+ tags.size(),false);

            for (int i = 0; i < tags.size(); i++) {
                 Logger.logLocalText2(tags.get(i).get("tag_l2")+"---",true);

            }
            if (tags!=null){
                //开启线程计数
                CountDownLatch _latch= new CountDownLatch(tags.size());;
                try {
                    //遍历爬取书籍的标签,
                    int tagSize=tags.size();
                    for (Record tag : tags) {//遍历标签，创建爬虫
                         //配置代理
                            String host=null;
                            String port=null;
                            String[] hostAndPort=ProxyManager.getHostAndPort();
                            host=hostAndPort[0];
                            port=hostAndPort[1];
                            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                            httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new us.codecraft.webmagic.proxy.Proxy(host,Integer.parseInt(port))));

                            //构造url，启动爬虫
                            int pages=tag.getInt("pages");
                            String tag_l1=tag.getStr("tag_l1");
                            String tag_l2=tag.getStr("tag_l2");
                            if (!StrUtils.isNull(pages+"",tag_l1,tag_l2)){
                                int startRow=pages*20;
                                new Spider(new BookPageProcessor(tag,tag.getInt("pages"),host,port),_latch,tagSize,tag_l2).setDownloader(httpClientDownloader).addUrl(SpiderConfig.BOOK_LIST_URL.replace("tag_list",tag_l2).replace("start_row",startRow+"")).start();
                                tagSize--;
                            }else {
                                Logger.logLocalText("标签出现错误",true);
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.logLocalText("创建爬虫时发生错误线程停止",true);
                }
                try {
                    _latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("####数据库获取不到未完成任务的爬虫标签，爬取结束");
                Logger.logLocalText("大爷找不到了",true);
                break;
            }
        }
        finishBookDetail();
    }


    public void finishBookDetail(){
        //获取所有的标签
        while (true){
            List<Record> tags=Db.find("select *  from tag where type='图书' and status<>2");
            if (tags.size()==0){
                break;
            }
            for (Record tag : tags) {
                int id=tag.getInt("id");
                List<Record> books= Db.find("select * from book where tag_id="+id+" and status =0");
                //获取所有的书籍
                    if (books.size()!=0){
                    int count=0;//设计标志位，如果标志位为0时的时候创建拿到代理，代理
                    String host= null;
                    String port= null;
                    for (Record book : books) {
                        if (count==0) {
                            String[] hostAndPort=ProxyManager.getHostAndPort();
                            host = hostAndPort[0];
                            port = hostAndPort[1];
                        }else if (count==10){
                            count=-1;
                            System.out.println("count为10，host为"+host+"，port为"+port+"");
                        }
                        new BookSpider(book.get("id").toString(),host,port).start();
                        count++;
                    }
                    }else {
                    Db.update("update tag set status=2 where id="+id);
                    }
            }
        }
    }

    //查询豆瓣电影电视剧
    public void queryMedia(){
    }

    //时间间隔
    public void threadSleep(int milles){
        try {
            Thread.sleep(milles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
