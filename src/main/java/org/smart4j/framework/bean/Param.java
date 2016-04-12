package org.smart4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数对象
 * Created by david.cai on 2016/4/11.
 */
public class Param {
    private Map<String , Object> paramMap = new HashMap<>();

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
