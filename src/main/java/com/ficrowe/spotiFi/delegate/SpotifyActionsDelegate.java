package com.ficrowe.spotiFi.delegate;

import com.ficrowe.spotiFi.model.SpotifyPlaybackState;
import com.ficrowe.spotiFi.model.SpotifyRequest;
import com.ficrowe.spotiFi.model.SpotifyRequestBuilder;
import com.ficrowe.spotiFi.model.spotify.*;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;

@Service
public class SpotifyActionsDelegate {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private final String SPOTIFY_API_BASE_URI = "https://api.spotify.com/v1";
  private final String SPOTIFY_ME_API_BASE_URI = "https://api.spotify.com/v1/me";
  private final String NEXT_TRACK_URI = SPOTIFY_ME_API_BASE_URI + "/player/next";
  private final String PREVIOUS_TRACK_URI = SPOTIFY_ME_API_BASE_URI + "/player/previous";
  private final String PLAY_URI = SPOTIFY_ME_API_BASE_URI + "/player/play";
  private final String PAUSE_URI = SPOTIFY_ME_API_BASE_URI + "/player/pause";
  private final String GET_PLAYBACK_STATE_URI = SPOTIFY_ME_API_BASE_URI + "/player";
  private final String GET_USERS_PLAYLISTS_URI = SPOTIFY_ME_API_BASE_URI + "/playlists";
  private final String GET_CURRENT_TRACK_URI =
      SPOTIFY_ME_API_BASE_URI + "/player/currently-playing";
  private final String ADD_REMOVE_TRACKS_TO_PLAYLIST_URI =
      SPOTIFY_API_BASE_URI + "/playlists/playlistId/tracks";

  @Autowired private RestTemplate restTemplate;

  @Autowired private SpotifyApi spotifyApi;

  public void retryAction(String failedAction) throws Exception {
    this.getClass().getDeclaredMethod(failedAction).invoke(this);
  }

  public SpotifyPlaylist[] getPlaylists() {
    SpotifyRequestBuilder<Void, SpotifyPlaylists> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, SpotifyPlaylists> spotifyPlaylistsRequest =
        spotifyRequestBuilder
            .setUri(GET_USERS_PLAYLISTS_URI)
            .setHttpMethod(HttpMethod.GET)
            .setResponseType(SpotifyPlaylists.class)
            .build();

    ResponseEntity<SpotifyPlaylists> response = executeSpotifyRequest(spotifyPlaylistsRequest);

    LOGGER.info(response.getBody().toString());
    LOGGER.info(response.toString());

    return response.getBody().getItems();
  }

  //    public SpotifyPlaylist getPlaylistById(String playlistId) {
  //        SpotifyRequestBuilder<Void, SpotifyPlaylist> spotifyRequestBuilder = new
  // SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
  //        SpotifyRequest<Void, SpotifyPlaylist> spotifyPlaylistRequest =
  // spotifyRequestBuilder.setUri(GET_USERS_PLAYLISTS_URI + "/" + playlistId)
  //                .setHttpMethod(HttpMethod.GET)
  //                .setResponseType(SpotifyPlaylist.class)
  //                .build();
  //
  //        ResponseEntity<SpotifyPlaylist> response =
  // executeSpotifyRequest(spotifyPlaylistRequest);
  //
  //        LOGGER.info(response.getBody().toString());
  //        LOGGER.info(response.toString());
  //
  //        return response.getBody();
  //    }

  public SpotifyPlaybackState playbackState() {
    SpotifyRequestBuilder<Void, SpotifyPlaybackState> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, SpotifyPlaybackState> spotifyPlaylistsRequest =
        spotifyRequestBuilder
            .setUri(GET_PLAYBACK_STATE_URI)
            .setHttpMethod(HttpMethod.GET)
            .setResponseType(SpotifyPlaybackState.class)
            .build();

    return executeSpotifyRequest(spotifyPlaylistsRequest).getBody();
  }

  @Retryable(retryFor = HttpClientErrorException.Unauthorized.class, maxAttempts = 1)
  public void play() {
    SpotifyRequestBuilder<Void, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(PLAY_URI).setHttpMethod(HttpMethod.PUT).build();

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  public void pause() {
    SpotifyRequestBuilder<Void, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(PAUSE_URI).setHttpMethod(HttpMethod.PUT).build();

    LOGGER.info(spotifyPlaylistsRequest.toString());

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  public void nextTrack() {
    SpotifyRequestBuilder<Void, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(NEXT_TRACK_URI).setHttpMethod(HttpMethod.POST).build();

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  public void previousTrack() {
    SpotifyRequestBuilder<Void, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(PREVIOUS_TRACK_URI).setHttpMethod(HttpMethod.POST).build();

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  public SpotifyTrack getCurrentTrack() {
    SpotifyRequestBuilder<Void, SpotifyGetCurrentTrackResponse> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<Void, SpotifyGetCurrentTrackResponse> spotifyPlaylistsRequest =
        spotifyRequestBuilder
            .setUri(GET_CURRENT_TRACK_URI)
            .setHttpMethod(HttpMethod.GET)
            .setResponseType(SpotifyGetCurrentTrackResponse.class)
            .build();

    return executeSpotifyRequest(spotifyPlaylistsRequest).getBody().getItem();
  }

  public void addCurrentTrackToPlaylist(String playlistId) {
    SpotifyTrack currentTrack = getCurrentTrack();
    SpotifyAddTracksToPlaylistRequest request =
        SpotifyAddTracksToPlaylistRequest.builder().uris(List.of(currentTrack.getUri())).build();
    addTracksToPlaylist(request, playlistId);
  }

  public void removeCurrentTrackFromPlaylist(String playlistId, String playlistSnapshotId) {
    SpotifyTrack currentTrack = getCurrentTrack();
    SpotifyRemoveTracksFromPlaylistRequest request =
        SpotifyRemoveTracksFromPlaylistRequest.builder()
            .tracks(List.of(new SpotifyTrackRequest(currentTrack.getUri())))
            .snapshotId(playlistSnapshotId)
            .build();
    removeTracksFromPlaylist(request, playlistId);
  }

  public void removeTracksFromPlaylist(
      SpotifyRemoveTracksFromPlaylistRequest request, String playlistId) {
    String uri = ADD_REMOVE_TRACKS_TO_PLAYLIST_URI.replaceAll("playlistId", playlistId);
    SpotifyRequestBuilder<SpotifyRemoveTracksFromPlaylistRequest, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<SpotifyRemoveTracksFromPlaylistRequest, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(uri).setBody(request).setHttpMethod(HttpMethod.DELETE).build();

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  public void addTracksToPlaylist(SpotifyAddTracksToPlaylistRequest request, String playlistId) {
    String uri = ADD_REMOVE_TRACKS_TO_PLAYLIST_URI.replaceAll("playlistId", playlistId);
    SpotifyRequestBuilder<SpotifyAddTracksToPlaylistRequest, Void> spotifyRequestBuilder =
        new SpotifyRequestBuilder<>(spotifyApi.getAccessToken());
    SpotifyRequest<SpotifyAddTracksToPlaylistRequest, Void> spotifyPlaylistsRequest =
        spotifyRequestBuilder.setUri(uri).setBody(request).setHttpMethod(HttpMethod.POST).build();

    executeSpotifyRequest(spotifyPlaylistsRequest);
  }

  private <T, K> ResponseEntity<K> executeSpotifyRequest(SpotifyRequest<T, K> spotifyRequest) {
    LOGGER.info("Executing Spotify API request {}", spotifyRequest);
    try {
      return restTemplate.exchange(
          spotifyRequest.getUri(),
          spotifyRequest.getHttpMethod(),
          spotifyRequest.asHttpEntity(),
          spotifyRequest.getResponseType());
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage(), exception);
      throw exception;
    }
  }

  private HttpHeaders httpHeaders() {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER + spotifyApi.getAccessToken());
    return headers;
  }

  private <T> HttpEntity<T> asHttpEntity(@Nullable T body) {
    return new HttpEntity<T>(body, httpHeaders());
  }

  private URI asURI(String uriString) {
    return URI.create(uriString);
  }
}
