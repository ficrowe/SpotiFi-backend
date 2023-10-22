package com.ficrowe.spotiFi.configuration;

import com.ficrowe.spotiFi.exception.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public RestTemplate restTemplate(
      @Autowired RestTemplateResponseErrorHandler responseErrorHandler) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(responseErrorHandler);
    return restTemplate;
  }
}
