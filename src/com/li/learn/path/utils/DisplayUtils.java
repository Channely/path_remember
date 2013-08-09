package com.li.learn.path.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;

public class DisplayUtils {

    private Display display;

    public DisplayUtils(Display display) {
        this.display = display;
    }


    public void getScreenSize(Point point) {
        display.getSize(point);
    }

    public void getRectSize(Rect size) {
        display.getRectSize(size);
    }
}
