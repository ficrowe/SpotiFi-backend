package com.ficrowe.spotiFi.model.device;

import lombok.Getter;

public enum DeviceType {
  MACROPAD("MACROPAD");

  @Getter private String value;

  DeviceType(String value) {
    this.value = value;
  }
}
