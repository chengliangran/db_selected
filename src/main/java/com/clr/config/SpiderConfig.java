package com.clr.config;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class SpiderConfig {
    public static String DB_URL_PREFIX="https://api.douban.com/v2/";
    public static String DB_BOOK_PREFIX=DB_URL_PREFIX+"/book/";
    public static String DB_MOVIE_PREFIX=DB_URL_PREFIX+"/movie/";
    public static String DB_MUSIC_PREFIX=DB_URL_PREFIX+"/music/";

    //
    public static String EPISODES_LIST_URL="https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电视剧,left_tags&start=0";
    public static String MOVIE_LIST_URL="https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电影,left_tags&start=0";
    public static String BOOK_LIST_URL="https://book.douban.com/tag/";

    public static String DETAIL_URL="https://movie.douban.com//subject/";

}
