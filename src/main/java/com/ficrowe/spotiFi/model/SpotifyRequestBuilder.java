package com.ficrowe.spotiFi.model;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

public class SpotifyRequestBuilder<T, K> {
  private final String AUTHORIZATION = "Authorization";
  private final String BEARER = "Bearer ";
  private final String accessToken;

  private HttpMethod httpMethod;

  @Nullable private T body;

  private Class<K> responseType;

  private URI uri;

  public SpotifyRequestBuilder(String accessToken) {
    this.accessToken = accessToken;
  }

  public SpotifyRequestBuilder<T, K> setUri(String uri) {
    this.uri = URI.create(uri);
    return this;
  }

  public SpotifyRequestBuilder<T, K> setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  public SpotifyRequestBuilder<T, K> setBody(T body) {
    this.body = body;
    return this;
  }

  public SpotifyRequestBuilder<T, K> setResponseType(Class<K> responseType) {
    this.responseType = responseType;
    return this;
  }

  public SpotifyRequest<T, K> build() {
    if (responseType == null) {
      this.responseType = (Class<K>) Void.class;
    }
    return new SpotifyRequest<T, K>(
        this.httpMethod, this.body, this.uri, this.responseType, httpHeaders());
  }

  private HttpHeaders httpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER + accessToken);
    return headers;
  }
}
