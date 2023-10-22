package com.ficrowe.spotiFi.model.device;

import com.ficrowe.spotiFi.constants.SpotifyShortcut;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceKey {

  private String id;

  private SpotifyShortcut shortcut;

  public Map<String, Object> toMap() {

    Map<String, Object> deviceKeyMap = new HashMap<>();
    deviceKeyMap.put("shortcut", shortcut.toMap());

    return deviceKeyMap;
  }
}
