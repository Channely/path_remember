package com.li.learn.demo05.domain;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.baidu.location.LocationClientOption;
import com.li.learn.demo05.R;
import com.li.learn.demo05.framework.BeanContext;

public class LocationView extends LinearLayout {
    private CheckBox priorityCheckbox;
    private CheckBox gpsCheckbox;
    private Button startLocBtn;
    private LocationFinder locationFinder;
    private TextView locAutoTextView;
    private EditText locRevisedEditText;

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        initLocationFinder();
    }

    private void initLocationFinder() {
        locationFinder = BeanContext.getInstance().getBean(LocationFinder.class);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loc_group, null, false);
        addView(view);

        priorityCheckbox = (CheckBox) findViewById(R.id.checkbox_priority);
        gpsCheckbox = (CheckBox) findViewById(R.id.checkbox_gps);
        startLocBtn = (Button) findViewById(R.id.btn_begin_loc);
        locAutoTextView = (TextView) findViewById(R.id.loc_auto_result);
        locRevisedEditText = (EditText) findViewById(R.id.loc_revise_result);
        startLocBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocation();
            }
        });
    }

    private void requestLocation() {
        locationFinder.setLocOption(getLocOption());
        if (!locationFinder.isStarted()) {
            locationFinder.start();
        }
        locationFinder.requestLocation();
    }

    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(gpsCheckbox.isChecked());
        option.setCoorType("bd09ll");
        option.setServiceName("com.li.learn.location.service");
        option.setAddrType("all");
        option.disableCache(true);
        if (priorityCheckbox.isChecked()) {
            option.setPriority(LocationClientOption.NetWorkFirst);
        } else {
            option.setPriority(LocationClientOption.GpsFirst);
        }
        option.setPoiNumber(5);
        return option;
    }


    public void setAutoLocation(final String location) {
        locAutoTextView.post(new Runnable() {
            @Override
            public void run() {
                locAutoTextView.setText(location);
            }
        });
    }


    public String getAutoLocation() {
        return locAutoTextView.getText().toString();
    }

    public String getRevisedLocation() {
        return locRevisedEditText.getText().toString();
    }
}
