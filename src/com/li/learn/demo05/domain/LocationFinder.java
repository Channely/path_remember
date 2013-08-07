package com.li.learn.demo05.domain;

import android.content.Context;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationFinder {

    private final LocationClient locationClient;
    private ReceiveLocationCallback receiveLocationCallback;

    public LocationFinder(Context appContext) {
        locationClient = new LocationClient(appContext);
        initClient();
    }

    private void initClient() {
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String addrStr = bdLocation.getAddrStr();
                if (receiveLocationCallback != null) {
                    receiveLocationCallback.receiveLocation(addrStr);
                }
                Log.d("location", addrStr);
            }

            @Override
            public void onReceivePoi(BDLocation bdLocation) {

            }
        });
    }

    public void addReceiveLocationCallback(ReceiveLocationCallback receiveLocationCallback) {
        this.receiveLocationCallback = receiveLocationCallback;
    }

    public void stop() {
        this.receiveLocationCallback = null;
        if (locationClient.isStarted()) {
            locationClient.stop();
        }
    }

    public void setLocOption(LocationClientOption option) {
        locationClient.setLocOption(option);
    }

    public void start() {
        locationClient.start();
    }

    public boolean isStarted() {
        return locationClient.isStarted();
    }
}
