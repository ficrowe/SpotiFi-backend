package com.ficrowe.spotiFi.exception;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

    return (httpResponse.getStatusCode().is4xxClientError()
        || httpResponse.getStatusCode().is5xxServerError());
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {

    if (httpResponse.getStatusCode().is5xxServerError()) {
      // handle SERVER_ERROR
      throw new HttpServerErrorException(httpResponse.getStatusCode());
    } else if (httpResponse.getStatusCode().is4xxClientError()) {
      if (httpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
        throw HttpClientErrorException.Unauthorized.create(
            HttpStatus.UNAUTHORIZED,
            httpResponse.getStatusText(),
            httpResponse.getHeaders(),
            httpResponse.getBody().readAllBytes(),
            null);
      }
      // handle CLIENT_ERROR
      throw new HttpClientErrorException(httpResponse.getStatusCode());
    }
  }
}
