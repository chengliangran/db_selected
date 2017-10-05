package com.clr.utils;

import com.clr.common.ConnectionManager;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class Tester1 {
    public static void main(String[] args) {
        new DB_Spider().queryBook();
        new DB_Spider().queryEpisodes();
        new DB_Spider().queryMovie();
    }
}
