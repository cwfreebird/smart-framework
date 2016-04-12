package org.smart4j.framework.helper;

import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手类
 * Created by david.cai on 2016/4/6.
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classeSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classeSet) {
            BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("cannot get bean by class:" + cls.getName());
        }
        return (T) BEAN_MAP.get(cls);
    }
}
