package com.ficrowe.spotiFi.api;

import lombok.Getter;

public enum SpotifyAuthorisationScope {
  USER_READ_PRIVATE("user-read-private"),
  PLAYLIST_READ_PRIVATE("playlist-read-private"),
  PLAYLIST_READ_COLLABORATIVE("playlist-read-collaborative"),
  USER_MODIFY_PLAYBACK_STATE("user-modify-playback-state"),
  USER_READ_PLAYBACK_STATE("user-read-playback-state"),
  USER_READ_CURRENTLY_PLAYING("user-read-currently-playing"),
  PLAYLIST_MODIFY_PUBLIC("playlist-modify-public"),
  PLAYLIST_MODIFY_PRIVATE("playlist-modify-private");

  @Getter private String value;

  SpotifyAuthorisationScope(String value) {
    this.value = value;
  }
}
