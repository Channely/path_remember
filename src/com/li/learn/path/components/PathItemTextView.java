package com.li.learn.path.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.li.learn.path.R;

public class PathItemTextView extends TextView {
    public PathItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    private void initUI() {
        setTextColor(R.color.normal_text);
        setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
    }
}
