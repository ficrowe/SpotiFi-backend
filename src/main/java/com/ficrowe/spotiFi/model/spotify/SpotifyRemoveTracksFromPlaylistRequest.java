package com.ficrowe.spotiFi.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpotifyRemoveTracksFromPlaylistRequest {
  private List<SpotifyTrackRequest> tracks;

  @JsonProperty("snapshot_id")
  private String snapshotId;
}
