package com.clr.common;

import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

import javax.sql.DataSource;
import java.io.*;
import java.net.URL;

/**
 * Created by Administrator on 2017/10/24 0024.
 */
public class DBGenerator {



    public static DataSource getDataSource(){
        return ConnectionManager.initConnection().getDataSource();
    }

    public static void main(String[] args) {
        //generator 使用的包名
        String baseModelPackageName="com.clr.model";
        //使用的物理文件路径
        String baseModelOutputDir= null;
        try {
            String url =DBGenerator.class.getResource("/").toURI().toString();
            URL url1=new URL(url);
            File file=new File(url1.toURI());
            File projectDir=file.getParentFile().getParentFile();
            String basicModelOutputFile=new File(projectDir.toString()+"/src/main/java/com/clr/model/base").toString();
            String basicModelPackageName="com.clr.model.base";

            String modelOutputFile=new File(projectDir.toString()+"/src/main/java/com/clr/model").toString();
            String modelPackageName="com.clr.model";
            Generator generator=new Generator(ConnectionManager.initConnection().getDataSource(),baseModelPackageName,basicModelOutputFile,modelPackageName,modelOutputFile);
            generator.setDialect(new MysqlDialect());
            generator.generate();
//            System.out.println(new MetaBuilder(ConnectionManager.initConnection().getDataSource()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
