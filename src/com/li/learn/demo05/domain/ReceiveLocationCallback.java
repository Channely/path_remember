package com.li.learn.demo05.domain;

public interface ReceiveLocationCallback {
    void fetchLocationError(String errorMsg);
    void receiveLocation(String location);
}
