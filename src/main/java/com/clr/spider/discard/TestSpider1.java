package com.clr.spider.discard;

import com.clr.common.ConnectionManager;
import com.clr.spider.processor.TestPageProcessor;
import us.codecraft.webmagic.Spider;

public class TestSpider1 {
    public static void main(String[] args) {
        ConnectionManager.initConnection();
//        new Spider(new TestPageProcessor(),).addUrl("https://bool.douban.com/tag").start();
    }
}
