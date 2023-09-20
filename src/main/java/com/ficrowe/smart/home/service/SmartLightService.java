package com.ficrowe.smart.home.service;

import com.ficrowe.smart.home.delegate.DynamoDBDelegate;
import com.ficrowe.smart.home.model.SmartLightSettings;
import com.ficrowe.smart.home.model.SmartLightStatus;
import com.ficrowe.smart.home.model.SmartLightStatusRequest;
import com.ficrowe.smart.home.model.SmartLightTimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmartLightService {

    @Autowired
    DynamoDBDelegate dynamoDBDelegate;


    /** LIST LIGHTS **/
    public List<SmartLightSettings> listLights() {
        return dynamoDBDelegate.getAllLights();
    }

    /** STATUS **/
    public SmartLightStatus getStatus(Long id) {
        return dynamoDBDelegate.getLightStatus(id);
    }
    public void setStatus(SmartLightStatusRequest request) {
        dynamoDBDelegate.setLightStatus(request.getDeviceId(), request.getStatus());
    }


    /** TIME ON **/
    public LocalDateTime getTimeOn(Long id) {
        return dynamoDBDelegate.getTimeOn(id);
    }

    public void handleTimeRequest(SmartLightTimeRequest request) {
        switch (request.getStatus()) {
            case ON -> setTimeOn(request.getDeviceId(), request.getTime());
            case OFF -> setTimeOff(request.getDeviceId(), request.getTime());
        }
    }

    private void setTimeOn(Long id, LocalDateTime timeOn) {
        dynamoDBDelegate.setTimeOn(id, timeOn);
    }

    /** TIME OFF **/
    public LocalDateTime getTimeOff(Long id) {
        return dynamoDBDelegate.getTimeOn(id);
    }

    private void setTimeOff(Long id, LocalDateTime timeOff) {
        dynamoDBDelegate.setTimeOff(id, timeOff);
    }
}