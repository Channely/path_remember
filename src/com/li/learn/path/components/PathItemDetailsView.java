package com.li.learn.path.components;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.li.learn.path.R;
import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.utils.DisplayUtils;
import com.li.learn.path.utils.ImageUtils;

public class PathItemDetailsView extends RelativeLayout {

    private ImageView imageView;

    public PathItemDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    private void initUI() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.path_item_details_view, null);
        addView(view);
        imageView = (ImageView) findViewById(R.id.path_item_details_view_image);
    }

    public void setImage(String imagePath) {
        Point point = new Point();
        (BeanContext.getInstance().getBean(DisplayUtils.class)).getScreenSize(point);
        imageView.setImageBitmap(ImageUtils.decodeBitmapFromFile(
                imagePath, point));

    }
}
