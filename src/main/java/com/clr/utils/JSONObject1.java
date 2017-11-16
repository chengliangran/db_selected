package com.clr.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class JSONObject1 {

    List<String> arr=new ArrayList<String>();

    JSONMap map=new JSONMap();

    boolean locked=false;

    public JSONObject1(){

    }

    public JSONObject1(com.alibaba.fastjson.JSONObject object){
        parseJSONMap(object);
    }

    public void parseJSONMap(com.alibaba.fastjson.JSONObject object){
        if (locked==false){
            parseJSONMap(null,object);
        }
    }

    public JSONMap getMap(){
        JSONMap returnMap=map;
        map=new JSONMap();
        arr=new ArrayList<String>();
        locked=false;
        return returnMap;
    }

    //解析json，并将其装入map
    private void parseJSONMap(String key, com.alibaba.fastjson.JSONObject object){
        for (Map.Entry<String,Object> entry: object.entrySet()){
            String subKey="";//配置下一级的key
            if (key!=null){
                subKey=key+"/"+entry.getKey();
            }else{
                subKey=entry.getKey();
            }
            String jsonStr=entry.getValue().toString();//配置下一级的值字符串
            if (jsonStr!=null&&!jsonStr.equals("")) {
                if (jsonStr.startsWith("{")&&jsonStr.endsWith("}")){ // 判断是否是对象
                    com.alibaba.fastjson.JSONObject subObject= JSON.parseObject(jsonStr);
                    parseJSONMap(subKey,subObject);// 递归处理json对象
                }else if(jsonStr.startsWith("[")&&jsonStr.endsWith("]")){ // 判断是否是数组
                    JSONArray array=JSON.parseArray(jsonStr);
                    parseArarry(subKey,array);
                }else {
                    map.put(subKey,entry.getValue());
                }
            }else{
                map.put(subKey,entry.getValue());
            }
        }
     }

    //解析数组
    private void parseArarry(String key,JSONArray array){ //递归解析数组
        for (int i = 0; i < array.size(); i++) {
            arr.add(key);
            String subKey=key+ "["+i+"]";
            String jsonStr=array.get(i).toString();
            if (jsonStr!=null&&!jsonStr.equals(null)) {
                if (jsonStr.startsWith("{")&&jsonStr.endsWith("}")){
                    com.alibaba.fastjson.JSONObject object= JSON.parseObject(jsonStr);
                    parseJSONMap(subKey,object);
                 }else if(jsonStr.startsWith("[")&&jsonStr.endsWith("]")){
                    JSONArray jsonArray=JSON.parseArray(jsonStr);
                    parseArarry(subKey,jsonArray);
                }else{
                    map.put(subKey,array.get(i));
                }
            }
        }
    }

    //解析规则
    public String path(String path){
        List<String> matchedWords=new ArrayList();
        if (StrUtils.isNull(path)){
            String[] words=path.split("/");
            for (String word : words) {
                for (int i = 0; i < arr.size(); i++) {
                    if (word.equals(arr.get(i))){
                        matchedWords.add(word);
                    }
                }
            }
        }
        if (matchedWords.size()>0){
            return null;
        }else {
            return map.getString(path);
        }

    }

    public String toString(){
        return getMap().getMap().toString();
    }
}
