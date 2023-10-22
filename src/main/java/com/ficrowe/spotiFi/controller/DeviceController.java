package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.model.device.Device;
import com.ficrowe.spotiFi.model.device.DeviceKey;
import com.ficrowe.spotiFi.service.DeviceService;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired private DeviceService deviceService;

  @GetMapping("/setup/{deviceId}")
  @CrossOrigin(origins = "http://127.0.0.1:5173")
  public void setupDevice(
      @PathVariable String deviceId, @RequestParam String name, @RequestHeader String userId) {
    LOGGER.info("setupDevice");
    deviceService.setupDevice(userId, deviceId, name);
  }

  @PostMapping("/create")
  public String createDevice(@RequestBody Device device)
      throws ExecutionException, InterruptedException {
    LOGGER.info("createDevice");
    return deviceService.createDevice(device);
  }

  @PostMapping("/{deviceId}/keys")
  @CrossOrigin(origins = "http://127.0.0.1:5173")
  public void addKeyToDevice(@PathVariable String deviceId, @RequestBody DeviceKey deviceKey) {
    LOGGER.info("addKeyToDevice");
    deviceService.addKeyToDevice(deviceId, deviceKey);
  }
}
