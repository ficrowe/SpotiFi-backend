package com.ficrowe.spotiFi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
public class SpotifyTokens {

  @JsonProperty("access_token")
  private final String accessToken;

  @JsonProperty("refresh_token")
  private final String refreshToken;

  @JsonProperty("token_type")
  @Nullable
  private final String tokenType;

  @JsonProperty("expires_in")
  @Nullable
  private final String expiresIn;
}
