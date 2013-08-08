package com.li.learn.path.domain;

public interface ReceiveLocationCallback {
    void fetchLocationError(String errorMsg);
    void receiveLocation(String location);
}
