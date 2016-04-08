package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具类
 * Created by david.cai on 2016/3/4.
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     * @param str
     * @return
     */
    public static boolean isNotEmpyt(String str){
        return !isEmpty(str);
    }
}
