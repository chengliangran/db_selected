package com.clr.spider.processor;

import com.jfinal.plugin.activerecord.Record;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

public class MoviePageProcessor extends MediaPageProcessor{

    public MoviePageProcessor(Record record) {
        super(record);
    }

    @Override
    public void process(Page page) {
        System.out.println("开始豆瓣电影页面的处理");
    }
}
