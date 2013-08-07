package com.li.learn.demo05.framework;

import java.util.HashMap;
import java.util.Map;

public class BeanContext {
    private Map<Class, Object> beans;
    private static BeanContext instance;

    private BeanContext() {
        beans = new HashMap<Class, Object>();
    }

    public synchronized static BeanContext getInstance() {
        if (instance == null) {
            instance = new BeanContext();
        }
        return instance;
    }

    public <T> void putBean(Class<T> clazz, T bean) {
        beans.put(clazz, bean);
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}
