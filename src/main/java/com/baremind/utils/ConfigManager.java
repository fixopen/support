package com.baremind.utils;

import com.baremind.algorithm.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by fixopen on 4/6/15.
 */
public class ConfigManager {
    public static Config parseConfig(String configFilePath) {
        Config result = null;
        return result;
    }

    public static void saveConfig(Config config, String configFilePath) {
        //
    }

    public static void main(String[] args) {
        Properties props = new Properties();

        InputStream in = ConfigManager.class.getResourceAsStream("../config.properties");

        // 或使用文件输入流(不推荐)，假设当前工作目录为bin
        //InputStream in = new FileInputStream("./config.properties");

        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取特定属性
        String key = "ip";
        String ip = props.getProperty(key);

        // 遍历所有属性，方式一
        Set keys = props.keySet();
        for (Iterator it = keys.iterator(); it.hasNext(); ) {
            String k = (String)it.next();
            System.out.println(k + ":" + props.getProperty(k));
        }
        // 遍历所有属性，方式二
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String k = (String)en.nextElement();
            System.out.println(k + ":" + props.getProperty(k));
        }
        // 遍历所有属性，方式三
        props.forEach((name, value) -> {
            System.out.println(name + ":" + value);
        });
    }
}
