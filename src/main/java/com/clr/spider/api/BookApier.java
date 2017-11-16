package com.clr.spider.api;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
public class BookApier {
    String host=null;
    String port=null;
    String id=null;

    public BookApier(String id) {
        this.id = id;
    }

    public BookApier(String host, String port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }
    public void process(){


    }

}
