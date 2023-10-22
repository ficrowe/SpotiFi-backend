package com.ficrowe.spotiFi.constants;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum SpotifyShortcutArgument {
  SONG("song"),
  PLAYLIST("playlist"),
  SNAPSHOT_ID("snapshot_id");

  @Getter @JsonValue @JsonKey private String value;

  SpotifyShortcutArgument(String value) {
    this.value = value;
  }
}
