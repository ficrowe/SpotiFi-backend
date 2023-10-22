package com.ficrowe.spotiFi.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpotifyPlaylist {
  private String id;
  private String name;

  //    private List<SpotifyTrack> tracks;

  @JsonProperty("snapshot_id")
  private String snapshotId;
}
