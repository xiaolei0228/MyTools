package com.ehome.file.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 读取properties文件
 */
public class PropertiesFileUtil implements java.io.Serializable {
    private static String defaultPropertyFile = "system.properties";

    private static String path;

    private static Properties props;

    //静态块 初始化
    static {
        props = new Properties();
        path = PropertiesFileUtil.class.getClassLoader().getResource("/") + defaultPropertyFile;
        File file;
        try {
            file = new File(new URI(path));
            if (!file.exists()) {//配置文件找不到，就创建
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 获取属性文件的值
     * @param key
     */
    public static String getValue(String key) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(new URI(path))));
            props.load(in);
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置属性文件的值
     * @param key
     * @param value
     */
    public static void setValue(String key, String value) {
        try {
            OutputStream ops = new FileOutputStream(new File(new URI(path)));
            props.setProperty(key, value);
            props.store(ops, "set");
        } catch (IOException e) {
            System.out.println("Visit " + " for updating " + key + " value error");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




}
