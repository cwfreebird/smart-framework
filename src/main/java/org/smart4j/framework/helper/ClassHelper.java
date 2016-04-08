package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;
import org.smart4j.framework.util.PropsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * Created by david.cai on 2016/4/6.
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        CLASS_SET = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage());
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> set = new HashSet<>();
        for (Class cls : CLASS_SET){
            if (cls.isAnnotationPresent(Controller.class)){
                set.add(cls);
            }
        }
        return set;
    }

    /**
     * 获取所有service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> set = new HashSet<>();
        for (Class cls : CLASS_SET){
            if (cls.isAnnotationPresent(Service.class)){
                set.add(cls);
            }
        }
        return set;
    }

    /**
     * 获取所有bean
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> set = new HashSet<>();
        set.addAll(getControllerClassSet());
        set.addAll(getServiceClassSet());
        return set;
    }
}
