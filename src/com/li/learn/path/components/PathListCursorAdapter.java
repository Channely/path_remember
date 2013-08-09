package com.li.learn.path.components;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import com.li.learn.path.domain.PathItemDBOperator;

public class PathListCursorAdapter extends CursorAdapter {

    public PathListCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        PathItemView view = new PathItemView(context);
        view.setText(cursor.getString(cursor.getColumnIndex(PathItemDBOperator.TITLE_COLUMN)));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PathItemView pathItemView = (PathItemView) view;
        pathItemView.setText(cursor.getString(cursor.getColumnIndex(PathItemDBOperator.TITLE_COLUMN)));
    }
}
