package com.clr.spider.processor;

import com.jfinal.plugin.activerecord.Record;
//import com.jfinal.template.stat.ast.Else;
import com.sun.prism.impl.Disposer;
import org.apache.http.HttpHost;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class BasicPageProcessor implements PageProcessor {

    Site site=Site.me().setTimeOut(10000);

    Record tag=null;

    BasicPageProcessor(Record tag){
        System.out.println(tag);
        this.tag=tag;
        System.out.println(123);
    }

    public void process(Page page) {
        System.out.println(tag);
        System.out.println("开始处理页面");
    }

    public Site getSite() {
        return site;
    }

    public String getName(Page page,String xpath){
         if(page!=null&&xpath!=null){
            return page.getHtml().xpath(xpath).toString();
        }else {
            System.out.println("page为空");
            return null;
        }
    }

    public String getId(Page page,String xpath){
       if (page!=null&&xpath!=null){
           String href= page.getHtml().xpath(xpath).toString();
           int index= 0;
           try {
               index = href.indexOf("subject")+8;
           } catch (Exception e) {
               e.printStackTrace();
               System.out.println(page);
           }
           if (index!=-1&&href!=null){
               return href.substring(index).replace("/","");
           }else {
               System.out.println("没有获取到指定的href");
               return null;
           }
       }else {
           System.out.println("page为空");
           return null;
       }
    }

}
