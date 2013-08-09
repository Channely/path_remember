package com.li.learn.path.components;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import com.li.learn.path.domain.PathItem;
import com.li.learn.path.utils.Constants;

import java.util.Date;

public class PathListCursorAdapter extends CursorAdapter {

    public PathListCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        PathItemView view = new PathItemView(context);
        view.fillWithPathItem(createPathItemFromCursor(cursor));
        return view;
    }

    private PathItem createPathItemFromCursor(Cursor cursor) {
        PathItem pathItem = new PathItem();
        pathItem.setCategory(getTextFromCursor(cursor, Constants.CATEGORY_COLUMN));
        pathItem.setTitle(getTextFromCursor(cursor, Constants.TITLE_COLUMN));
        pathItem.setBus(getTextFromCursor(cursor, Constants.BUS_COLUMN));
        pathItem.setAutoLocation(getTextFromCursor(cursor, Constants.AUTO_LOCATION_COLUMN));
        pathItem.setRevisedLocation(getTextFromCursor(cursor, Constants.REVISED_LOCATION_COLUMN));
        pathItem.setCreatedAt(new Date((cursor.getLong(cursor.getColumnIndex(Constants.CREATED_DATE_COLUMN)))));
        pathItem.setThumbnailImagePath(getTextFromCursor(cursor, Constants.THUMBNAIL_IMAGE_PATH_COLUMN));
        pathItem.setFullImagePath(getTextFromCursor(cursor, Constants.FULL_IMAGE_PATH_COLUMN));
        return pathItem;
    }

    private String getTextFromCursor(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PathItemView pathItemView = (PathItemView) view;
        pathItemView.fillWithPathItem(createPathItemFromCursor(cursor));
    }
}
