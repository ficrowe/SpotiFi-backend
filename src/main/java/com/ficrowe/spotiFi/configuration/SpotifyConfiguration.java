package com.ficrowe.spotiFi.configuration;

import com.ficrowe.spotiFi.model.SpotifyTokens;
import com.ficrowe.spotiFi.util.TokenFileUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;

@Configuration
public class SpotifyConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyConfiguration.class);

  @Value("${spotify.client.id}")
  private String spotifyClientId = "343ccd37c2e54fb6ae5953e026521607";

  @Value("${spotify.client.secret}")
  private String spotifyClientSecret = "a673a737886840d4b2b098346c513bbd";

  private final URI redirectUri =
      URI.create("http://localhost:8080/spotify/authentication/callback");

  @Bean
  public SpotifyApi spotifyApi() throws UnknownHostException {
    System.out.println(InetAddress.getLocalHost().getHostAddress());

    SpotifyApi.Builder spotifyApiBuilder = spotifyApiBuilder();
    try {
      SpotifyTokens spotifyTokens = TokenFileUtil.readTokenFile();
      spotifyApiBuilder.setAccessToken(spotifyTokens.getAccessToken());
      spotifyApiBuilder.setRefreshToken(spotifyTokens.getRefreshToken());
    } catch (IOException exception) {
      LOGGER.error(exception.getMessage(), exception);
    }
    return spotifyApiBuilder
        .setRedirectUri(URI.create("http://localhost:8080/spotify/authentication/callback"))
        .build();
    //        return SpotifyApi.builder()
    //                .setClientId(spotifyClientId)
    //                .setClientSecret(spotifyClientSecret)
    //                .setRedirectUri(redirectUri)
    //                .build();
    //        return SpotifyConfiguration.staticSpotifyApi();
  }

  public static SpotifyApi.Builder spotifyApiBuilder() {
    return SpotifyApi.builder()
        .setClientId("343ccd37c2e54fb6ae5953e026521607")
        .setClientSecret("a673a737886840d4b2b098346c513bbd");
  }

  public static SpotifyApi spotifyApiWithRedirect(URI redirectUri) {
    return spotifyApiBuilder().setRedirectUri(redirectUri).build();
  }

  public static SpotifyApi staticSpotifyApi() {
    SpotifyApi.Builder spotifyApiBuilder = spotifyApiBuilder();
    try {
      SpotifyTokens spotifyTokens = TokenFileUtil.readTokenFile();
      spotifyApiBuilder.setAccessToken(spotifyTokens.getAccessToken());
      spotifyApiBuilder.setRefreshToken(spotifyTokens.getRefreshToken());
    } catch (IOException exception) {
      LOGGER.error(exception.getMessage(), exception);
    }
    return spotifyApiBuilder
        .setRedirectUri(URI.create("http://localhost:8080/spotify/authentication/callback"))
        .build();
  }
}
