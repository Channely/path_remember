package com.li.learn.path.components;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.li.learn.path.R;
import com.li.learn.path.utils.Constants;
import com.li.learn.path.utils.StringUtils;

public class PathItemView extends RelativeLayout {
    private static final int VIEW_HEIGHT = 150;
    private TextView titleView;
    private TextView categoryView;
    private TextView busView;
    private TextView autoLocationView;
    private TextView revisedLocationView;
    private ImageView thumbnailImageView;

    public PathItemView(Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.path_item_view, this, false);
        this.setLayoutParams(new ListView.LayoutParams(LayoutParams.WRAP_CONTENT, VIEW_HEIGHT));
        addView(view);
        thumbnailImageView = (ImageView) view.findViewById(R.id.path_item_thumbnail_image);
        titleView = (TextView) view.findViewById(R.id.path_item_title);
        categoryView = (TextView) view.findViewById(R.id.path_item_category);
        busView = (TextView) view.findViewById(R.id.path_item_bus);
        autoLocationView = (TextView) view.findViewById(R.id.path_item_auto_location);
        revisedLocationView = (TextView) view.findViewById(R.id.path_item_revised_location);
    }

    public void fillWithDataFromCursor(Cursor cursor) {
        thumbnailImageView.setImageBitmap(createBitmap(cursor));
        categoryView.setText(getTextFromCursor(cursor, Constants.CATEGORY_COLUMN));
        titleView.setText(getTextFromCursor(cursor, Constants.TITLE_COLUMN));
        busView.setText(getTextFromCursor(cursor, Constants.BUS_COLUMN));
        autoLocationView.setText(getTextFromCursor(cursor, Constants.AUTO_LOCATION_COLUMN));
        revisedLocationView.setText(getTextFromCursor(cursor, Constants.REVISED_LOCATION));
    }

    private Bitmap createBitmap(Cursor cursor) {
        if (hasThumbnail(cursor)) {
            return BitmapFactory.decodeFile(getTextFromCursor(cursor, Constants.THUMBNAIL_IMAGE_PATH_COLUMN));
        }
        return BitmapFactory.decodeResource(getResources(), R.drawable.im_thumbnail_no_image);
    }

    private boolean hasThumbnail(Cursor cursor) {
        return !StringUtils.isEmpty(getTextFromCursor(cursor, Constants.THUMBNAIL_IMAGE_PATH_COLUMN));
    }

    private String getTextFromCursor(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
