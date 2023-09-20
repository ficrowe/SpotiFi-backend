package com.ficrowe.smart.home.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmartLightTimeRequest {
    private Long deviceId;
    private SmartLightStatus status;
    private LocalDateTime time;
}
