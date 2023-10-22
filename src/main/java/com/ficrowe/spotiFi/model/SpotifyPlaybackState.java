package com.ficrowe.spotiFi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpotifyPlaybackState {
  @JsonProperty("is_playing")
  private Boolean isPlaying;
}
