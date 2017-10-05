package com.clr.spider.api;

import com.clr.config.SpiderConfig;

public class DoubanMovieAPIer extends BasicAPIer implements Runnable{

    String movieUrl= SpiderConfig.DB_MOVIE_PREFIX;

    float interval=15;

    String sql="select * from movie order by id desc";

    public void queryMovies(){
        new Thread(new DoubanMovieAPIer()).start();
    }

    public void run() {
        queryByAPI(movieUrl,sql,interval);
    }
}


