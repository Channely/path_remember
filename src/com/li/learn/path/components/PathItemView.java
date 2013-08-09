package com.li.learn.path.components;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.li.learn.path.R;
import com.li.learn.path.domain.PathItem;

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

    public void fillWithPathItem(PathItem pathItem) {
        if (!pathItem.hasThumbnailImage()) {
            thumbnailImageView.setImageResource(R.drawable.im_thumbnail_no_image);
        } else {
            thumbnailImageView.setImageBitmap(pathItem.getThumbnailImage());
        }
        categoryView.setText(pathItem.getCategory());
        titleView.setText(pathItem.getTitle());
        busView.setText(pathItem.getBus());
        autoLocationView.setText(pathItem.getAutoLocation());
        revisedLocationView.setText(pathItem.getRevisedLocation());
    }

    private String getTextFromCursor(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
