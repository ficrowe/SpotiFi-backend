package com.ficrowe.spotiFi.delegate;

import com.ficrowe.spotiFi.api.SpotifyAuthorisationScope;
import com.ficrowe.spotiFi.util.TokenFileUtil;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@Service
public class SpotifyAuthDelegate {

  private String redirectUrl;

  private final String DEFAULT_REDIRECT_URL = "http://127.0.0.1:5173/home";
  private final String LOGIN_REDIRECT_URL = "http://127.0.0.1:5173/login";

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final String SCOPE =
      Arrays.stream(SpotifyAuthorisationScope.values())
          .map(SpotifyAuthorisationScope::getValue)
          .reduce((String allScopes, String scope) -> allScopes + " " + scope)
          .get();

  @Autowired private SpotifyApi spotifyApi;

  private AuthorizationCodeUriRequest authorizationCodeUriRequest() {
    return spotifyApi
        .authorizationCodeUri()
        //          .state("x4xkmn9pu3j6ukrs8n")
        .scope(SCOPE)
        //          .show_dialog(true)
        .build();
  }

  public URI authorizationCodeUri() {
    URI uri = authorizationCodeUriRequest().execute();
    LOGGER.info("AuthorizationCodeUriRequest returned {}", uri);
    return uri;
  }

  public void setCode(String code) {
    LOGGER.info("Setting SpotifyApi authorisationCode {}", code);
    authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
  }

  private AuthorizationCodeRequest authorizationCodeRequest;

  public void setRedirectUrl(String redirectUri) {
    LOGGER.info("Setting redirect url to {}", redirectUri);
    redirectUrl = redirectUri;
  }

  public String refreshAccessToken() throws IOException, HttpClientErrorException {
    if (spotifyApi.getRefreshToken() != null) {
      return authorizationCode(spotifyApi.getRefreshToken());
    } else {
      LOGGER.error("Unable to refresh token as refreshToken is not set.");
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Cannot refresh access token");
    }
  }

  public String authorizationCode(String code) throws IOException {
    setCode(code);
    try {
      final AuthorizationCodeCredentials authorizationCodeCredentials =
          authorizationCodeRequest.execute();
      LOGGER.info("Retrieved AuthorizationCodeCredentials {}", authorizationCodeCredentials);

      // Set access and refresh token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

      TokenFileUtil.writeTokenFile(
          authorizationCodeCredentials.getAccessToken(),
          authorizationCodeCredentials.getRefreshToken());

      LOGGER.info("Expires in: " + authorizationCodeCredentials.getExpiresIn());

      if (redirectUrl != null) {
        return redirectUrl;
      }
      return DEFAULT_REDIRECT_URL;
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      LOGGER.error(e.getMessage(), e);
      return LOGIN_REDIRECT_URL;
    }
  }
}
