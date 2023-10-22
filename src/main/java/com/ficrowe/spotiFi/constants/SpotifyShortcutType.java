package com.ficrowe.spotiFi.constants;

import lombok.Getter;

public enum SpotifyShortcutType {
  ADD_CURRENT_TRACK_TO_PLAYLIST("ADD_CURRENT_TRACK_TO_PLAYLIST"),
  REMOVE_CURRENT_TRACK_FROM_PLAYLIST("REMOVE_CURRENT_TRACK_FROM_PLAYLIST"),
  PAUSE("PAUSE"),
  PLAY("PLAY"),
  NEXT("NEXT"),
  PREVIOUS("PREVIOUS");

  @Getter private String value;

  SpotifyShortcutType(String value) {
    this.value = value;
  }
}
