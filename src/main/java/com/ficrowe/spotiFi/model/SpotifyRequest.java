package com.ficrowe.spotiFi.model;

import java.net.URI;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

@Builder
@Data
public class SpotifyRequest<T, K> {

  private final HttpMethod httpMethod;

  @Nullable private T body;
  private URI uri;

  private Class<K> responseType;

  private HttpHeaders headers;

  public HttpEntity<T> asHttpEntity() {
    if (this.body == null) {
      return new HttpEntity<T>(headers);
    }
    return new HttpEntity<T>(body, headers);
  }
}
