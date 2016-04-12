package org.smart4j.framework.bean;

import java.util.Map;

/**
 * Created by david.cai on 2016/4/11.
 */
public class Data {
    private Object modelObject;

    public Data(Object object) {
        this.modelObject = object;
    }

    public Object getModelObject() {
        return modelObject;
    }
}
