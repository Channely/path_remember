package com.li.learn.path.components;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.li.learn.path.R;
import com.li.learn.path.domain.PathItemDBOperator;
import com.li.learn.path.framework.BeanContext;

public class PathItemList extends ListView {

    private PathListCursorAdapter cursorAdapter;

    public PathItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        initAdapter();
    }

    private void initAdapter() {
        cursorAdapter = new PathListCursorAdapter(getContext(), getQueryCursor());
        setAdapter(cursorAdapter);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout oneLineHeader = (LinearLayout) inflater.inflate(R.layout.oneline_header, this, false);
        addHeaderView(oneLineHeader);
    }

    public void refresh() {
        cursorAdapter.changeCursor(getQueryCursor());
        cursorAdapter.notifyDataSetChanged();
    }

    private Cursor getQueryCursor() {
        PathItemDBOperator dbOperator = BeanContext.getInstance().getBean(PathItemDBOperator.class);
        return dbOperator.getQueryCursor();
    }
}
