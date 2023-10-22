package com.ficrowe.spotiFi.model.shortcut;

import lombok.Data;

@Data
public class DeviceKeyPressRequest {
  private String deviceId;
  private String deviceKeyId;
}
