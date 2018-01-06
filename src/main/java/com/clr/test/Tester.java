package com.clr.test;

import com.clr.common.ConnectionManager;
import com.clr.spider.DoubanQuerier;
import com.clr.common.ProxyManager;
import com.clr.utils.Logger;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class Tester {
    public static void main(String[] args) {
            new DoubanQuerier().queryBook();
    }
}

class Worker implements Runnable{
    public void run() {
        Repo.addProduct(Thread.currentThread().getName());
    }
    public void startWorking(){
        Thread thread=new Thread(this);
        thread.start();
    }
}

class Repo{
    public synchronized static void addProduct(String name){
        for (int i = 0; i < 10; i++) {
            System.out.println("线程名为"+name+"线程正在执"+i+"次操作 ");
            System.out.println("ceshi");
        }
    }
}

