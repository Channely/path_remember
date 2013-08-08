package com.li.learn.path.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.li.learn.path.R;

public class PathItemView extends RelativeLayout {

    private TextView textView;

    public PathItemView(Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.path_item_view, this, false);
        this.setLayoutParams(new ListView.LayoutParams(LayoutParams.WRAP_CONTENT, 60));
        addView(view);
        textView = (TextView) view.findViewById(R.id.text);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
