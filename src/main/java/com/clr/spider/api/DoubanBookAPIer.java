package com.clr.spider.api;

import com.clr.config.SpiderConfig;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class DoubanBookAPIer extends BasicAPIer implements Runnable{

    String booksUrl=SpiderConfig.DB_BOOK_PREFIX;

    float interval=10;

    String sql="select * from book order by id desc";

    //查询图书
    public void queryBooks(){
        new Thread(new DoubanBookAPIer()).start();
    }

    public void run() {
        queryByAPI(booksUrl,sql,interval);
    }
}
