package com.ficrowe.spotiFi.model;

import org.springframework.http.HttpEntity;

public interface SpotifyApiRequestBuilder<T> {
  HttpEntity<T> build();
}
