package com.ficrowe.smart.home.controller;

import com.ficrowe.smart.home.model.SmartLightSettings;
import com.ficrowe.smart.home.model.SmartLightStatus;
import com.ficrowe.smart.home.model.SmartLightStatusRequest;
import com.ficrowe.smart.home.model.SmartLightTimeRequest;
import com.ficrowe.smart.home.service.SmartLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lights")
public class SmartLightController {

    @Autowired
    private SmartLightService smartLightService;

    /** LIST LIGHTS **/
    @GetMapping("/list")
    public List<SmartLightSettings> listLights() {
        return smartLightService.listLights();
    }

    /** STATUS **/
    @GetMapping("/status/{id}")
    public SmartLightStatus getStatus(Long id) {
        return smartLightService.getStatus(id);
    }

    @PostMapping("/status")
    public void setStatus(@RequestBody SmartLightStatusRequest request) {
        smartLightService.setStatus(request);
    }

    /** TIME ON **/
    @GetMapping("/timeOn/{id}")
    public LocalDateTime getTimeOn(Long id) {
        return smartLightService.getTimeOn(id);
    }

    @PostMapping("/timeOn")
    public void setTimeOn(@RequestBody SmartLightTimeRequest request) {
        smartLightService.handleTimeRequest(request);
    }


    /** TIME OFF **/
    @GetMapping("/timeOff/{id}")
    public LocalDateTime getTimeOff(Long id) {
        return smartLightService.getTimeOff(id);
    }

    @PostMapping("/timeOff")
    public void setTimeOff(@RequestBody SmartLightTimeRequest request) {
        smartLightService.handleTimeRequest(request);
    }
}