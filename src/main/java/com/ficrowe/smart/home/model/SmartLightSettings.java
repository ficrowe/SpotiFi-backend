package com.ficrowe.smart.home.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SmartLightSettings {

    private Long deviceId;
    private String name;
    private SmartLightStatus status;
    private LocalDate timeOn;
    private LocalDate timeOff;
}

