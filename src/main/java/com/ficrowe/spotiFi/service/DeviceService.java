package com.ficrowe.spotiFi.service;

import com.ficrowe.spotiFi.delegate.FirestoreDelegate;
import com.ficrowe.spotiFi.model.device.Device;
import com.ficrowe.spotiFi.model.device.DeviceKey;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

  @Autowired private FirestoreDelegate firestoreDelegate;

  public void setupDevice(String userId, String deviceId, String deviceName) {
    firestoreDelegate.linkUserToDevice(userId, deviceId, deviceName);
  }

  public String createDevice(Device device) throws ExecutionException, InterruptedException {
    return firestoreDelegate.addDevice(device);
  }

  public List<Device> getUserDevices(String userId)
      throws ExecutionException, InterruptedException {
    return firestoreDelegate.getUserDevices(userId);
  }

  public Device getDevice(String deviceId) throws ExecutionException, InterruptedException {
    return firestoreDelegate.getDevice(deviceId);
  }

  public void addKeyToDevice(String deviceId, DeviceKey deviceKey) {
    firestoreDelegate.addKeyToDevice(deviceId, deviceKey);
  }
}
