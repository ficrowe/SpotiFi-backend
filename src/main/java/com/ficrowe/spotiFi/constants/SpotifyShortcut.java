package com.ficrowe.spotiFi.constants;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class SpotifyShortcut {

  private String id;
  private SpotifyShortcutType type;

  @JsonSerialize private Map<SpotifyShortcutArgument, String> inputs;

  public Map<String, Object> toMap() {
    Map<String, Object> shortcutMap = new HashMap<>();
    shortcutMap.put("id", id);
    shortcutMap.put("type", type.getValue());

    if (inputs != null) {
      Map<String, Object> inputsMap = new HashMap<>();
      inputs.forEach(
          (key, value) -> {
            inputsMap.put(key.getValue(), value);
          });
      shortcutMap.put("inputs", inputsMap);
    }

    return shortcutMap;
  }
}
