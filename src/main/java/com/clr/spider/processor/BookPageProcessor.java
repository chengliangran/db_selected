package com.clr.spider.processor;

import com.clr.common.ConnectionManager;
import com.clr.config.SpiderConfig;
import com.clr.model.Book;
import com.clr.spider.subject.BookSpider;
import com.clr.utils.Logger;
import com.clr.utils.StrUtils;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.http.HttpHost;
import org.apache.poi.util.StringUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;


public class BookPageProcessor extends BasicPageProcessor {

    public int currenPageNum=-1;

    public Site site = super.getSite();

    public Record tag=super.tag;

    public String host=null;

    public String port=null;

    public BookPageProcessor(Record tag) {
        super(tag);
        System.out.println(123);
    }

    public BookPageProcessor(Record tag,int currenPageNum,String host,String port){
        super(tag);
        this.currenPageNum=currenPageNum;
        this.host=host;
        this.port=port;
    }

    public Site getSite() {
        return site;
    }

    @Override
    //可以同时处理list页面和
    public void process(Page page) {
        super.process(page);
        //判断是否已经结尾
        boolean hasNoNextPage=false;
        System.out.println(page.getHtml().toString());
        System.out.println(page.getHtml().toString().contains("<link rel=\"next\" href="));
        //*[@id="subject_list"]/div[2]/span[4]/a

        if (page.toString().contains("没有找到符合条件的图书")){//页面处理完毕
            System.out.println("####页面已经结束，标签是"+tag.getStr("tag_l2"));
            Db.update("update tag set pages=-1,final_page="+tag.getInt("pages")+" where id="+tag.getInt("id"));
        }
        processList(page,hasNoNextPage);
     }

    private void processList(Page page,boolean hasNextPage) {
        if (page.getUrl().toString().startsWith(SpiderConfig.BOOK_LIST_URL.substring(0,28))){
            boolean foundItem=false;
            int range=20;
            //拿到页面上面的数据
            for (int i = 1; i < range; i++) {
                String name=getName(page, SpiderConfig.FIELD_NAME.replace("index",i+""));
                String id= null;
                try {
                    id = getId(page, SpiderConfig.FIELD_ID.replace("index",i+""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (!StrUtils.isNull(name,id)){
                        foundItem=true;
                        Db.update("insert into book(id,name,tag_id) values('"+id+"','"+name+"',"+tag.getInt("id")+")");//存入当前页面所有的图书信息
                        //获取书籍的详细信息
                        new BookSpider(id,host,port).start();
                     }
                } catch (Exception e) {
                     e.printStackTrace();
                }
            }

            //如果当前页面没有任何数据，那么停止抓取，页面处理错误
            if (!foundItem){
                try {
                    System.out.print("####获取的是有问题的页面，操作结束");
                    Logger.logLocalText("这是出问题的网页"+page.getUrl(),false);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //更新抓取页面的数据并且添加新的页面
            int currentPage=tag.getInt("pages")+1;
            tag.set("pages",currentPage);
            Db.update("update tag set pages="+currentPage+" where id="+tag.getInt("id"));
            page.addTargetRequest(SpiderConfig.BOOK_LIST_URL.replace("tag_list",tag.getStr("tag_l2")).replace("start_row",(currentPage*20)+""));
            threadSleep(1000);
        }else{
            System.out.println("####未获取到正确的豆瓣页面，没有对页面进行操作");
        }
    }

    public void threadSleep(int milles){
        try {
            Thread.sleep(milles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
