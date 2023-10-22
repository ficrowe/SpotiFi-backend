package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.delegate.SpotifyActionsDelegate;
import com.ficrowe.spotiFi.model.SpotifyPlaybackState;
import com.ficrowe.spotiFi.model.spotify.SpotifyPlaylist;
import com.ficrowe.spotiFi.model.spotify.SpotifyTrack;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

  private Logger LOGGER = LoggerFactory.getLogger(SpotifyController.class);

  @Autowired private SpotifyActionsDelegate spotifyActionsDelegate;

  @GetMapping("/retry")
  public void retryAction(@RequestParam String action) throws Exception {
    spotifyActionsDelegate.retryAction(action);
  }

  @GetMapping("/play")
  public void play() {
    LOGGER.info("GET /play");
    spotifyActionsDelegate.play();
  }

  @GetMapping("/pause")
  public void pause() {
    LOGGER.info("GET /pause");
    spotifyActionsDelegate.pause();
  }

  @GetMapping("/next")
  public void next() {
    LOGGER.info("GET /next");
    spotifyActionsDelegate.nextTrack();
  }

  @GetMapping("/previous")
  public void previous() {
    LOGGER.info("GET /previous");
    spotifyActionsDelegate.previousTrack();
  }

  @GetMapping("/currentTrack")
  public SpotifyTrack currentTrack() {
    LOGGER.info("GET /currentTrack");
    return spotifyActionsDelegate.getCurrentTrack();
  }

  @GetMapping("/addCurrentTrackToPlaylist")
  public void addCurrentTrackToPlaylist() {
    LOGGER.info("GET /addCurrentTrackToPlaylist");
    SpotifyPlaylist[] playlists = spotifyActionsDelegate.getPlaylists();
    String playlistId =
        Arrays.stream(playlists)
            .filter((SpotifyPlaylist playlist) -> playlist.getName().equals("octobre"))
            .findFirst()
            .get()
            .getId();
    spotifyActionsDelegate.addCurrentTrackToPlaylist(playlistId);
  }

  @GetMapping("/playbackState")
  public SpotifyPlaybackState playbackState() {
    LOGGER.info("GET /playbackState");
    return spotifyActionsDelegate.playbackState();
  }

  @GetMapping("/playlists")
  public ResponseEntity<SpotifyPlaylist[]> playlists(HttpServletResponse httpServletResponse) {
    LOGGER.info("GET /playlists");
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
    try {
      return ResponseEntity.ok(spotifyActionsDelegate.getPlaylists());
    } catch (HttpClientErrorException.Unauthorized unauthorizedException) {
      return ResponseEntity.status(unauthorizedException.getStatusCode()).build();
    }
  }

  //    @GetMapping("/playlists/{playlistId}")
  //    public ResponseEntity<SpotifyPlaylist> getPlaylistById(@PathVariable String playlistId,
  // HttpServletResponse httpServletResponse) {
  //        LOGGER.info("GET /playlists/" + playlistId);
  //        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
  //        try {
  //            return ResponseEntity.ok(spotifyActionsDelegate.getPlaylistById(playlistId));
  //        } catch (HttpClientErrorException.Unauthorized unauthorizedException) {
  //            return ResponseEntity.status(unauthorizedException.getStatusCode()).build();
  //        }
  //    }
}
