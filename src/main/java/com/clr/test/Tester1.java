package com.clr.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class Tester1 {
    public static void main(String[] args) {

    }
    public void testMap(){
        Map map=new HashMap();
        map.put("1","1");
        map.put("2","2");
        map.put("2","2");
        map.put("3","3");
        System.out.println(map);
    }
}
