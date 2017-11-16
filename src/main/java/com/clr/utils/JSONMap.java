package com.clr.utils;

import java.util.*;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class JSONMap {

    Map<String ,Object> map=new HashMap<String,Object>();

    List<String> list=new ArrayList();

    public void put(String key,Object val){
            map.put(key,val);
    }

    public Object getObject(String key){
       return map.get(key);
    }

    public int getInt(String key){
        return (Integer) map.get(key);
    }

    public float getFloat(String key){
        return (Float) map.get(key);
    }

    public String getString(String key){
        return (String)map.get(key);
    }

    public Map<String,Object> getMap(){
        return  map;
    }

}
