package com.li.learn.path;

import android.app.Application;
import com.li.learn.path.domain.LocationFinder;
import com.li.learn.path.domain.PathItemDBOperator;
import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.framework.SerialPersistence;

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
        beanContext.putBean(SerialPersistence.class, new SerialPersistence(this));
    }

}
