package com.li.learn.path;

import android.app.Application;
import android.view.WindowManager;
import com.li.learn.path.domain.LocationFinder;
import com.li.learn.path.domain.PathItemDBOperator;
import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.framework.SerialPersistence;
import com.li.learn.path.utils.DisplayUtils;

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
        beanContext.putBean(DisplayUtils.class, new DisplayUtils(((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay()));
    }

}
