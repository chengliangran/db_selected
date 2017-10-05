package com.clr;

import com.clr.common.ConnectionManager;
import com.clr.spider.api.DoubanBookAPIer;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class Tester {
    public static void main(String[] args) {
        ConnectionManager.initConnection();
//        new DoubanMovieAPIer().queryMovies();
        new DoubanBookAPIer().queryBooks();


    }
}



