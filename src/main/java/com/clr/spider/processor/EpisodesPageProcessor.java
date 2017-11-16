package com.clr.spider.processor;

import com.jfinal.plugin.activerecord.Record;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class EpisodesPageProcessor extends MediaPageProcessor {

    public EpisodesPageProcessor(Record record){
        super(record);
    }

    public void process(Page page) {
        System.out.println("开始豆瓣电视剧页面的处理");
    }

    public Site getSite() {
        return null;
    }
}
