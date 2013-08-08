package com.li.learn.path.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathItemListAdapter extends BaseAdapter {
    private List<String> data = new ArrayList<String>();
    private Context context;

    public PathItemListAdapter(Context context, String[] originalData) {
        this.context = context;
        data.addAll(Arrays.asList(originalData));
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PathItemView listItem;
        if (convertView == null) {
            listItem = new PathItemView(context);
            listItem.setText((String) getItem(position));
        } else {
            listItem = (PathItemView) convertView;
            listItem.setText((String) getItem(position));
        }
        return listItem;
    }

    public void appendData(String[] data) {
        this.data.addAll(Arrays.asList(data));
        notifyDataSetChanged();
    }
}
