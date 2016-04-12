package org.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Reqeust;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器辅助类
 * Created by david.cai on 2016/4/11.
 */
public class ControllerHelper {
    private static final Map<Reqeust, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtil.isNotEmpty(controllerClassSet)){
            for (Class<?> controllerClass : controllerClassSet){
                Method[] methods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isNotEmpty(methods)){
                    for (Method method : methods){
                        if (method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();

                            if (mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                                    Reqeust reqeust = new Reqeust(array[0], array[1]);

                                    Handler handler = new Handler(controllerClass, method);

                                    ACTION_MAP.put(reqeust, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath){
        return ACTION_MAP.get(new Reqeust(requestMethod, requestPath));
    }
}
