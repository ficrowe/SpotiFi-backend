package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.model.device.Device;
import com.ficrowe.spotiFi.service.DeviceService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{userId}")
public class UserController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired private DeviceService deviceService;

  @GetMapping("/devices")
  @CrossOrigin(origins = "http://127.0.0.1:5173")
  public List<Device> getUserDevices(@PathVariable String userId)
      throws ExecutionException, InterruptedException {
    LOGGER.info("getUserDevices");
    List<Device> userDevices = deviceService.getUserDevices(userId);
    LOGGER.info("User devices {}", userDevices);
    return userDevices;
  }

  @GetMapping("/device/{id}")
  @CrossOrigin(origins = "http://127.0.0.1:5173")
  public Device getUserDevice(@PathVariable String userId, @PathVariable String id)
      throws ExecutionException, InterruptedException {
    LOGGER.info("getUserDevice {}", id);
    Device device = deviceService.getDevice(id);
    LOGGER.info("Devices {}", device);
    return device;
  }
}
