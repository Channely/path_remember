package com.li.learn.path.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.li.learn.path.R;
import com.li.learn.path.domain.PathItemDBOperator;
import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.tasks.FetchDataTask;

public class PathItemList extends ListView {

    private LoadingArea loadingArea;
    private boolean isGettingMore;
    private PathListCursorAdapter cursorAdapter;

    public PathItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        initAdapter();
    }

    private void initAdapter() {
        BeanContext beanContext = BeanContext.getInstance();
        PathItemDBOperator dbOperator = beanContext.getBean(PathItemDBOperator.class);
        cursorAdapter = new PathListCursorAdapter(getContext(), dbOperator.getQueryCursor());
        setAdapter(cursorAdapter);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout oneLineHeader = (LinearLayout) inflater.inflate(R.layout.oneline_header, this, false);
        addHeaderView(oneLineHeader);
        loadingArea = new LoadingArea(getContext());
    }

    public void showMore(int historyTotal) {
        if (getFooterViewsCount() > 0) return;
        addFooterView(loadingArea);
        loadMoreData(historyTotal);
    }

    private void loadMoreData(int historyTotal) {
        FetchDataTask fetchDataTask = new FetchDataTask(this);
        fetchDataTask.execute(historyTotal);
    }

    public boolean isGettingMore() {
        return isGettingMore;
    }

    public void appendData(String[] data) {
        hideLoadingMore();
        setNotGettingMore();
    }

    private void setNotGettingMore() {
        isGettingMore = false;
    }

    private void hideLoadingMore() {
        if (getFooterViewsCount() > 0) {
            removeFooterView(loadingArea);
        }
    }
}
