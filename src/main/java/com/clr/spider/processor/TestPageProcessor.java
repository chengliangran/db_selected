package com.clr.spider.processor;

import com.jfinal.plugin.activerecord.Db;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import javax.swing.text.html.HTML;
import java.util.List;

public class TestPageProcessor implements PageProcessor {

    public Site site = Site.me();

    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        //抓取
        Html html=page.getHtml();
        int type_l1_count=7;
        for (int i = 1; i < type_l1_count; i++) {
            String tagPath="//*[@id=\"content\"]/div/div[1]/div[2]/div["+i+"]/a/h2/allText()";
            String tagsPath="//*[@id=\"content\"]/div/div[1]/div[2]/div["+i+"]/table";
            String tagName= html.xpath(tagPath).toString();
            List<String> tagsStr=html.xpath(tagsPath).xpath("tbody/tr/td/a/allText()").all();
            for (String tag : tagsStr) {
                Db.update("insert into tag(type,tag_l1,tag_l2) values('图书','"+tagName.replace("· · · · · ·","").toString()+"','"+tag+"')");
            }
        }
        //第一层 种类
//        String[] types={"电影","电视剧"};
//        for (int i = 0; i < types.length; i++) {
//            String type = types[i];
//            //第二层 国家
//            String[] countries={"中国","美国","日本","韩国"};
//            for (int j = 0; j < countries.length; j++) {
//                String country = countries[j];
//                //第三层 种类
//                String[] tags={"剧情","爱情","喜剧","科幻","动作","悬疑","犯罪","恐怖","青春","励志","战争","文艺","黑色","幽默","传记","情色","暴力","音乐","家庭"};
//                for (String tag : tags) {
//                    System.out.println("insert into tag(type,tag_l1,tag_l2) values('"+type+"','"+country+"','"+tag+"')");
//                    Db.update("insert into tag(type,tag_l1,tag_l2) values('"+type+"','"+country+"','"+tag+"')");
//                }
//             }
//        }
     }
}
