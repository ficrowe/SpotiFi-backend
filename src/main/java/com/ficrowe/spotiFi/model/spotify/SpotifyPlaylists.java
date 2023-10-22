package com.ficrowe.spotiFi.model.spotify;

import lombok.Data;

@Data
public class SpotifyPlaylists {

  private String href;

  private SpotifyPlaylist[] items;
}
