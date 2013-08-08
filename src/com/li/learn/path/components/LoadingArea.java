package com.li.learn.path.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.li.learn.path.R;

public class LoadingArea extends LinearLayout {
    public LoadingArea(Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_area, this, false);
        this.setLayoutParams(new ListView.LayoutParams(LayoutParams.WRAP_CONTENT, 60));
        this.addView(view);
    }
}
