package com.ficrowe.smart.home.model;

import lombok.Data;

@Data
public class SmartLightStatusRequest {
    private Long deviceId;
    private SmartLightStatus status;
}
