package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ThreadFactory;

/**
 * 属性文件工具类
 * Created by david.cai on 2016/3/4.
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载指定属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            if (in == null){
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(in);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure ", e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性(默认值为空)
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key){
        return props.getProperty(key, "");
    }
    /**
     * 获取字符型属性(可指定默认值)
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue){
        return props.getProperty(key, defaultValue);
    }
}
