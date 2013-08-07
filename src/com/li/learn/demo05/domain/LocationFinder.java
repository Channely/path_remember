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
    private BDLocationListener bdLocationListener;

    public LocationFinder(Context appContext) {
        locationClient = new LocationClient(appContext);
        initClient();
    }

    private void initClient() {
        bdLocationListener = createLocationListener();
        locationClient.registerLocationListener(bdLocationListener);
    }

    private BDLocationListener createLocationListener() {
        return new BDLocationListener() {
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
                Log.d("location onReceivePoi:", bdLocation.getAddrStr());
            }
        };
    }

    public void addReceiveLocationCallback(ReceiveLocationCallback receiveLocationCallback) {
        this.receiveLocationCallback = receiveLocationCallback;
    }

    public void destroy() {
        locationClient.unRegisterLocationListener(bdLocationListener);
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
