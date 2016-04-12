package org.smart4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * Created by david.cai on 2016/4/11.
 */
public class View {
    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
