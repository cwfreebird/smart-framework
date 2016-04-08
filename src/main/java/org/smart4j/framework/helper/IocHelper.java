package org.smart4j.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by david.cai on 2016/4/7.
 */
public class IocHelper {

    static {
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();

        if (MapUtils.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)){
                    for (Field field : fields) {
                        Class<?> fieldClass = field.getType();
                        if (fieldClass.isAnnotationPresent(Inject.class)){
                            Object fieldInstance = beanMap.get(fieldClass);

                            if (fieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, field, fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
