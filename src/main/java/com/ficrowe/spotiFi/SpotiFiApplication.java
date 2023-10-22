package com.ficrowe.spotiFi;

import com.ficrowe.spotiFi.configuration.ApplicationConfiguration;
import com.ficrowe.spotiFi.configuration.FirebaseConfiguration;
import com.ficrowe.spotiFi.configuration.SpotifyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
    scanBasePackages = {"com.ficrowe.spotiFi"},
    exclude = {SecurityAutoConfiguration.class})
@Import({ApplicationConfiguration.class, FirebaseConfiguration.class, SpotifyConfiguration.class})
public class SpotiFiApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpotiFiApplication.class, args);
  }
}
