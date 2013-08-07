package com.li.learn.demo05.domain;

import com.li.learn.demo05.framework.BeanContext;

public class PathItem {

    private String fullImagePath;
    private String thumbnailImagePath;
    private String description;

    public PathItem(String fullImagePath, String thumbnailImagePath) {
        this.fullImagePath = fullImagePath;
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getFullImagePath() {
        return fullImagePath;
    }


    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean save() {
        return getPathItemDBOperator().savePathItem(this) >= 0;
    }


    private PathItemDBOperator getPathItemDBOperator() {
        BeanContext beanContext = BeanContext.getInstance();
        return beanContext.getBean(PathItemDBOperator.class);
    }
}
