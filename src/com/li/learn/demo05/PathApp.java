package com.li.learn.demo05;

import android.app.Application;
import com.li.learn.demo05.domain.LocationFinder;
import com.li.learn.demo05.domain.PathItemDBOperator;
import com.li.learn.demo05.framework.BeanContext;

public class PathApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBeans();
    }

    private void initBeans() {
        BeanContext beanContext = BeanContext.getInstance();
        beanContext.putBean(PathItemDBOperator.class, new PathItemDBOperator(this));
        beanContext.putBean(LocationFinder.class, new LocationFinder(this));
    }

}
