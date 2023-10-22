package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.delegate.SpotifyAuthDelegate;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/spotify/authentication")
public class SpotifyAuthenticationController {

  private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  @Autowired private SpotifyAuthDelegate spotifyAuthDelegate;

  @GetMapping("/authorise")
  public void authorise(HttpServletResponse httpServletResponse) throws IOException {
    LOGGER.info("/authorise");
    //        httpServletResponse.setHeader(LOCATION,
    // spotifyAuthDelegate.authorizationCodeUri().toString());
    //        httpServletResponse.setStatus(HttpStatus.SC_MOVED_TEMPORARILY);
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
    httpServletResponse.sendRedirect(spotifyAuthDelegate.authorizationCodeUri().toString());
  }

  @GetMapping("/authorise/redirect")
  public void authoriseWithRedirectUrl(
      @RequestParam String redirectUrl, HttpServletResponse httpServletResponse)
      throws IOException {
    LOGGER.info("/authorise/redirect {}", redirectUrl);
    //        httpServletResponse.setHeader(LOCATION,
    // spotifyAuthDelegate.authorizationCodeUri().toString());
    //        httpServletResponse.setStatus(HttpStatus.SC_MOVED_TEMPORARILY);
    spotifyAuthDelegate.setRedirectUrl(redirectUrl);
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
    httpServletResponse.sendRedirect(spotifyAuthDelegate.authorizationCodeUri().toString());
  }

  @GetMapping("/callback")
  public void callback(HttpServletResponse httpServletResponse, @RequestParam String code)
      throws IOException {
    LOGGER.info("/callback");
    httpServletResponse.sendRedirect(spotifyAuthDelegate.authorizationCode(code));
  }
}
