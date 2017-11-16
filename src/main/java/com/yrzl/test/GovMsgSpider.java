package com.yrzl.test;

import com.clr.utils.IOUtils;
import com.clr.utils.Logger;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class GovMsgSpider {

    String urlStr="http://xxgk.ah.gov.cn/UserData/SortHtml/702/GK781258932.html";

    String xpath="//*[@id=\"container\"]/div[2]/div[2]/div[3]";

    public static void main(String[] args) {
        new GovMsgSpider().process();
    }
    public void process(){
        try {
            URL url=new URL(urlStr);
            URLConnection connection= url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");


            connection.connect();
            InputStream inputStream= connection.getInputStream();
            String page= IOUtils.convertStreamToString(inputStream);
            List<String> as =new Html(page).xpath(xpath).xpath("//a").all();

            Logger log=new Logger("D:\\jiucuo.txt");
            log.logLocalText3("123",true);

            for (int i = 0; i < as.size(); i++) {
                try {
                    String href= new Html(as.get(i)).xpath("//a/@href").toString();
                    System.out.println(href);
                    new Spider(new GovPageProcessor(i,href)).addUrl(href).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

//            System.out.println(as.size());
//            System.out.println(as);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
