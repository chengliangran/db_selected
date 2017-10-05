package com.clr.spider;

import com.clr.common.ConnectionManager;
import us.codecraft.webmagic.Spider;

public class TestSpider1 {
    public static void main(String[] args) {
        ConnectionManager.initConnection();
        new Spider(new TestPageProcessor()).addUrl("https://bool.douban.com/tag").start();
    }
}
