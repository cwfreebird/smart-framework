package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装action信息
 * Created by david.cai on 2016/4/11.
 */
public class Handler {
    private Class<?> controllerClass;
    private Method method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
