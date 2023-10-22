package com.ficrowe.spotiFi.model.device;

import java.util.List;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
  private String id;
  private String name;
  private DeviceType type;
  private DeviceConfiguration configuration;

  private List<DeviceKey> keys;

  @Nullable private String userId;
}
