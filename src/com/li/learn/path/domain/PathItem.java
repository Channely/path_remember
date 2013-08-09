package com.li.learn.path.domain;

import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.utils.Constants;

import java.io.File;
import java.io.Serializable;

import static com.li.learn.path.utils.ImageUtils.decodeBitmapFromFile;
import static com.li.learn.path.utils.ImageUtils.saveBitmap;

public class PathItem implements Serializable {

    private String fullImagePath;
    private String thumbnailImagePath;
    private String category;
    private int selectedCategoryPosition;
    private String title;
    private String bus;
    private String autoLocation;
    private String revisedLocation;

    public PathItem() {
    }

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

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getAutoLocation() {
        return autoLocation;
    }

    public void setAutoLocation(String autoLocation) {
        this.autoLocation = autoLocation;
    }

    public String getRevisedLocation() {
        return revisedLocation;
    }

    public void setRevisedLocation(String revisedLocation) {
        this.revisedLocation = revisedLocation;
    }

    public boolean save() {
        if (hasTakeImage()) {
            boolean saveThumbnailResult = saveBitmap(getThumbnailImagePath(),
                    decodeBitmapFromFile(getFullImagePath(), Constants.PATH_ITEM_THUMBNAIL_IMAGE_WIDTH, Constants.PATH_ITEM_THUMBNAIL_IMAGE_HEIGHT));
            return saveThumbnailResult && getPathItemDBOperator().savePathItem(this) >= 0;
        }
        return getPathItemDBOperator().savePathItem(this) >= 0;
    }


    private boolean hasTakeImage() {
        return new File(getFullImagePath()).exists();
    }

    private PathItemDBOperator getPathItemDBOperator() {
        BeanContext beanContext = BeanContext.getInstance();
        return beanContext.getBean(PathItemDBOperator.class);
    }


    public int getSelectedCategoryPosition() {
        return selectedCategoryPosition;
    }

    public void setSelectedCategoryPosition(int selectedCategoryPosition) {
        this.selectedCategoryPosition = selectedCategoryPosition;
    }

}
