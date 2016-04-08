package org.smart4j.framework.util;

/**
 * 类型转换工具类
 * Created by david.cai on 2016/3/4.
 */
public class CastUtil {
    /**
     * 转为string
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return CastUtil.castString(obj, "");
    }

    /**
     * 转为string，提供默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj, String defaultValue){
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    /**
     * 转为double类型
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转为double类型，可设置默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj, double defaultValue){
        double value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpyt(strValue)){
                try {
                    value = Double.valueOf(strValue);
                } catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为logn
     * @param obj
     * @return
     */
    public static long castLong(Object obj){
        return castLong(obj, 0);
    }

    /**
     * 转换为long，可设置默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj, long defaultValue){
        long value = defaultValue;
        String strValue = castString(obj);

        if (StringUtil.isNotEmpyt(strValue)){
            try {
                value = Long.valueOf(strValue);
            } catch (NumberFormatException e){
                value = defaultValue;
            }
        }
        return  value;
    }

    /**
     * 转换为int
     * @param obj
     * @return
     */
    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    /**
     * 转换为int，设置默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        String strValue = castString(obj);

        if (StringUtil.isNotEmpyt(strValue)){
            try {
                value = Integer.valueOf(strValue);
            } catch (NumberFormatException e){
                value = defaultValue;
            }
        }
        return value;
    }

    /**
     * 转换为boolean
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj, false);
    }

    /**
     * 转换为boolean,设置默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        String strValue = castString(obj);

        if (StringUtil.isNotEmpyt(strValue)){
            try {
                value = Boolean.valueOf(strValue);
            } catch (NumberFormatException e){
                value = defaultValue;
            }
        }
        return value;
    }
}
