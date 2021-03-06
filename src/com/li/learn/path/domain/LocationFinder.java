package com.li.learn.path.domain;

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
                if (receiveLocationCallback == null) return;
                if (addrStr == null) {
                    receiveLocationCallback.fetchLocationError("无法定位当前地址");
                } else {
                    receiveLocationCallback.receiveLocation(addrStr);
                }
            }

            @Override
            public void onReceivePoi(BDLocation bdLocation) {
                Log.d("location onReceivePoi:", bdLocation.getAddrStr());
            }
        });
    }

    public void registerReceiveLocationCallback(ReceiveLocationCallback receiveLocationCallback) {
        this.receiveLocationCallback = receiveLocationCallback;
    }

    public void destroy() {
        receiveLocationCallback = null;
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

    public void requestLocation() {
        locationClient.requestLocation();
    }

    public boolean isStarted() {
        return locationClient.isStarted();
    }
}
