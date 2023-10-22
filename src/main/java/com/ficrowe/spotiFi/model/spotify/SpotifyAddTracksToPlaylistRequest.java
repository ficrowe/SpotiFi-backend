package com.ficrowe.spotiFi.model.spotify;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpotifyAddTracksToPlaylistRequest {
  private List<String> uris;
}
