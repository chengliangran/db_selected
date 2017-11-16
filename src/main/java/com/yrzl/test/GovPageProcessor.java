package com.yrzl.test;

import com.clr.utils.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class GovPageProcessor implements PageProcessor {

    int times=0;

    String href=null;

    public GovPageProcessor(int times,String href) {
        this.times = times;
        this.href=href;
    }

    public void process(Page page) {
        if (page==null){
            Logger log=new Logger("D:\\jiucuo.txt");
            log.logLocalText3(href,true);

        }
        Logger log=new Logger("D:\\page"+times+".txt");
        log.logLocalText3(page.getHtml().toString(),false);

    }

    public Site getSite() {
        return null;
    }
}
