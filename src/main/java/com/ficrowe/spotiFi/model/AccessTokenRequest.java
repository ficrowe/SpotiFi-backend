package com.ficrowe.spotiFi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class AccessTokenRequest {

  @JsonProperty("grant_type")
  private final String grant_type;

  @JsonProperty("client_id")
  private final String client_id;

  @JsonProperty("client_secret")
  private final String client_secret;
}
