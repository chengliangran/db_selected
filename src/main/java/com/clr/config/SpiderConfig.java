package com.clr.config;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class SpiderConfig {

    //豆瓣开发者api的url
    public static String DB_URL_PREFIX="https://api.douban.com/v2/";
    public static String DB_BOOK_PREFIX=DB_URL_PREFIX+"/book/";
    public static String DB_MOVIE_PREFIX=DB_URL_PREFIX+"/movie/";
    public static String DB_MUSIC_PREFIX=DB_URL_PREFIX+"/music/";

    //豆瓣页面集合的xpath （变量为tag_list,start_row）
    public static String EPISODES_LIST_URL="https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电视剧,tag_list&start=start_row";
    public static String MOVIE_LIST_URL="https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电影,tag_list&start=start_row";
    public static String BOOK_LIST_URL="https://book.douban.com/tag/tag_list?start=start_row&type=R";

    //豆瓣所有内容详情页面的xpath (变量为id')
    public static String BOOK_DETAIL_URL="https://book.douban.com//subject/id";
    public static String MOVIE_DETAIL_URL="https://book.douban.com//subject/id";

    //列表页面detail
    public static String FIELD_NAME="//*[@id=\"subject_list\"]/ul/li[index]/div[2]/h2/a/allText()";
    public static String FIELD_ID="//*[@id=\"subject_list\"]/ul/li[index]/div[2]/h2/a/@href";
    public static String FIELD_NEXTPAGE="//*[@id=\"subject_list\"]/div[2]/span[5]/a/@href";

    //书籍页面detail
    public static String FIELD_BOOK_TITLE="//*[@id=\"wrapper\"]/h1/span/allText()";
    public static String FIELD_BOOK_AUTHORS="//*[@id=\"info\"]/span[1]/a";//*[@id="info"]/a
    public static String FIELD_BOOK_TRANSLATOR="//*[@id=\"info\"]/span[3]/a";
    public static String FIELD_BOOK_IMAGE="//*[@id=\"mainpic\"]/a/img/@src";
    public static String FIELD_BOOK_SUMMARY="//*[@id=\"link-report\"]/div[1]/div";
    public static String FIELD_BOOK_CATALOG="//*[@id=\"content\"]/div/div[1]/div[3]/div[4]";
    public static String FIELD_BOOK_RATING="//*[@id=\"interest_sectl\"]/div/div[2]/strong/allText()";
    public static String FIELD_BOOK_NUMRATERS="//*[@id=\"interest_sectl\"]/div/div[2]/div/div[2]/span/a/span/allText()";
    public static String FIELD_BOOK_DETAIL="//*[@id=\"info\"]/html()";
    public static String FIELD_BOOK_PUBLISHERTAG="<span class=\"pl\">出版社:</span>";
    public static String FIELD_BOOK_PUBDATETAG="<span class=\"pl\">出版年:</span>";
    public static String FIELD_BOOK_PAGETAG="<span class=\"pl\">页数:</span>";
    public static String FIELD_BOOK_PRICETAG="<span class=\"pl\">定价:</span>";
    public static String FIELD_BOOK_ISBNTAG="<span class=\"pl\">ISBN:</span>";
}
