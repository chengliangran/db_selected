package com.clr.spider.subject;

import com.clr.common.ConnectionManager;
import com.clr.common.ProxyManager;
import com.clr.config.SpiderConfig;
import com.clr.utils.IOUtils;
import com.clr.utils.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/24 0024.
 */
public class BookSpider implements Runnable {
    String bookId=null;

    String host=null;

    String port=null;

    public BookSpider(String subjectId) {
        this.bookId = subjectId;
    }

    public BookSpider(String bookId, String host, String port) {
        this.bookId = bookId;
        this.host = host;
        this.port = port;
    }

    private void process(){
        try {
            //获取书籍详情页面
            System.out.println("获取书本id为"+ bookId);
            URL url=new URL(SpiderConfig.BOOK_DETAIL_URL.replace("id",bookId));
            URLConnection connection=null;
            if (!StrUtils.isNull(host,port)){
                connection=url.openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(host,Integer.parseInt(port))));
            }else {
                connection=url.openConnection();
            }
            connection.setReadTimeout(3000);
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            String page= IOUtils.convertStreamToString(inputStream);
            if (page!=null){
                //解析页面
                String TITLE=null;
                String authourIds="";
                //解析页面---获取详细条目
                StringBuilder AUTHORS=new StringBuilder();
                StringBuilder TRANSLATORS=new StringBuilder();
                String IMAGE="";
                String SUMMARY="";
                String CATALOG="";
                String DETAIL="";
                int pageNum= 0;
                double price= 0;
                String priceUnit="";
                String pubDate= "";
                String publisher= "";
                String isbn= "";
                double RATING=0;
                int NUMRATERS=0;
                TITLE=new Html(page).xpath(SpiderConfig.FIELD_BOOK_TITLE).toString();
                List<String> as=new Html(page).xpath("//*[@id=\"info\"]/span/a").all();
                as.addAll(new Html(page).xpath("//*[@id=\"info\"]/a").all());
                //解析author 和translator
                if ((((new Html(page).xpath("//*[@id=\"info\"]").toString()).contains("["))||((new Html(page).xpath("//*[@id=\"info\"]").toString()).contains("&middot;")))&&(new Html(page).xpath("//*[@id=\"info\"]").toString()).contains("译者")){
                    for (String a : as) {
                        if (!a.contains("series")) {
                            String author=new Html(a).xpath("/allText()").toString().trim();
                            String authorId="";
                            if (a.contains("[")||a.contains("&middot;")||Pattern.compile("[a-zA-Z\\.\\(]{1,}").matcher(new Html(a).xpath("/allText()").toString().trim()).find()){
                                if (a.contains("author")) {
                                    int index=(new Html(a).xpath("//a/@href").toString()).indexOf("r/")+2;
                                    authorId=new Html(a).xpath("//a/@href").toString().substring(index).replace("/","").trim();
                                } else {
                                    authorId="null";
                                }
                                AUTHORS.append(author+"-"+authorId+",");
                                System.out.println(123);
                            }else {
                                if (a.contains("author")) {
                                    int index=(new Html(a).xpath("//a/@href").toString()).indexOf("r/")+2;
                                    authorId=new Html(a).xpath("//a/@href").toString().substring(index).replace("/","").trim();
                                } else {
                                    authorId="null";
                                }
                                TRANSLATORS.append(author+"-"+authorId+",");
                            }
                        }
                    }
                }else {
                    for (String a : as) {
                        if (!a.contains("series")) {
                            String author=new Html(a).xpath("/allText()").toString().trim();
                            String authorId="";
                            if (a.contains("author")) {
                                String href=new Html(a).xpath("//a/@href").toString();
                                int index=href.indexOf("r/")+2;
                                authorId=href.substring(index).replace("/","").trim();
                            } else {
                                authorId="null";
                            }
                            AUTHORS.append(author+"-"+authorId+",");
                        }
                    }
                }
                IMAGE=new Html(page).xpath(SpiderConfig.FIELD_BOOK_IMAGE).toString();
                SUMMARY=new Html(page).xpath(SpiderConfig.FIELD_BOOK_SUMMARY).toString();
                CATALOG=new Html(page).xpath(SpiderConfig.FIELD_BOOK_CATALOG).toString();
                DETAIL=new Html(page).xpath(SpiderConfig.FIELD_BOOK_DETAIL).toString();
                try {
                    String pageNumStr = StrUtils.subString(""+ SpiderConfig.FIELD_BOOK_PAGETAG+"[^<]{0,}<",DETAIL).replace(SpiderConfig.FIELD_BOOK_PAGETAG,"").replace("<","").trim().replace("页","");
                    try {
                        pageNum=Integer.parseInt(StrUtils.subString("[0-9//.]{0,}",pageNumStr));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    String priceStr =StrUtils.subString(""+ SpiderConfig.FIELD_BOOK_PRICETAG+"[^<]{0,}<",DETAIL).replace(SpiderConfig.FIELD_BOOK_PRICETAG,"").replace("<","").trim();
                    try {
                        price=Double.parseDouble(StrUtils.subString("[0-9\\.]{1,}",priceStr));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (priceStr.contains("元")){
                        priceUnit="元";
                    }
                    publisher = StrUtils.subString(""+ SpiderConfig.FIELD_BOOK_PUBLISHERTAG+"[^<]{0,}<",DETAIL).replace(SpiderConfig.FIELD_BOOK_PUBLISHERTAG,"").replace("<","").trim();
                    pubDate = StrUtils.subString(""+ SpiderConfig.FIELD_BOOK_PUBDATETAG+"[^<]{0,}<",DETAIL).replace(SpiderConfig.FIELD_BOOK_PUBDATETAG,"").replace("<","").trim();
                    pubDate=pubDate+"-01";
                    isbn = StrUtils.subString(""+ SpiderConfig.FIELD_BOOK_ISBNTAG+"[^<]{0,}<",DETAIL).replace(SpiderConfig.FIELD_BOOK_ISBNTAG,"").replace("<","").trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    RATING=Double.parseDouble(new Html(page).xpath(SpiderConfig.FIELD_BOOK_RATING).toString().replace("元",""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    NUMRATERS=Integer.parseInt(new Html(page).xpath(SpiderConfig.FIELD_BOOK_NUMRATERS).toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                //验证数据

                //将书籍存入数据库
                if (!StrUtils.isNull(TITLE)){
                    Object[] params={TITLE,AUTHORS.toString(),TRANSLATORS.toString(),IMAGE,SUMMARY,CATALOG,pageNum,price,pubDate,publisher,isbn,RATING,NUMRATERS,priceUnit};
                    Db.update("update book set title=?,authors=?,translators=?,image=?,summary=?,catalog=?,pages=?,price=?,pubdate=?,publisher=?,isbn13=?,rating=?,numRaters=?,priceUnit=?,status=1 where id="+Integer.parseInt(bookId),params);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Db.update("update book set status=-1 where id="+bookId+"");
        } catch (IOException e) {
            e.printStackTrace();
            Db.update("update book set status=-1 where id="+bookId+"");
        }
    }

    public static void main(String[] args) {
        ConnectionManager.initConnection();
        new ProxyManager().start();
        new BookSpider(4048083+"").process();
    }

    public void run() {
        process();
    }
    public void start(){
        Thread t=new Thread(this);
        t.start();
    }
}
