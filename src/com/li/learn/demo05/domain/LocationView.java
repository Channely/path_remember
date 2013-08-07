package com.li.learn.demo05.domain;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.location.LocationClientOption;
import com.li.learn.demo05.R;
import com.li.learn.demo05.framework.BeanContext;

public class LocationView extends LinearLayout implements ReceiveLocationCallback {
    private static final int SCAN_INTERVAL_IN_MILLISECONDS = 3000;
    private CheckBox priorityCheckbox;
    private CheckBox gpsCheckbox;
    private Button startLocBtn;
    private LocationFinder locationFinder;
    private boolean isLocStart;
    private TextView locAutoTextView;

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        initLocationFinder();
    }

    private void initLocationFinder() {
        locationFinder = BeanContext.getInstance().getBean(LocationFinder.class);
        locationFinder.addReceiveLocationCallback(this);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loc_group, null, false);
        addView(view);

        priorityCheckbox = (CheckBox) findViewById(R.id.checkbox_priority);
        gpsCheckbox = (CheckBox) findViewById(R.id.checkbox_gps);
        startLocBtn = (Button) findViewById(R.id.btn_begin_loc);
        locAutoTextView = (TextView) findViewById(R.id.loc_auto_result);
        startLocBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoc();
            }
        });
    }

    private void startLoc() {
        if (isLocStart) return;
        locationFinder.setLocOption(getLocOption());
        locationFinder.start();
        isLocStart = true;
    }

    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(gpsCheckbox.isChecked());
        option.setCoorType("bd09ll");
        option.setServiceName("com.li.learn.location.service");
        option.setPoiExtraInfo(true);
        option.setScanSpan(SCAN_INTERVAL_IN_MILLISECONDS);
        option.setAddrType("all");
        if (priorityCheckbox.isChecked()) {
            option.setPriority(LocationClientOption.NetWorkFirst);
        } else {
            option.setPriority(LocationClientOption.GpsFirst);
        }
        option.setPoiNumber(10);
        option.disableCache(false);
        return option;
    }


    @Override
    public void receiveLocation(final String location) {
        Handler handler = getHandler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                locAutoTextView.setText(location);
            }
        });
    }
}
