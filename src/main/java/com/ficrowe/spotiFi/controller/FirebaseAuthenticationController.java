package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.delegate.FirestoreDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/firebase/authentication")
public class FirebaseAuthenticationController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired private FirestoreDelegate firestoreDelegate;

  @PostMapping("/register/{userId}")
  public void register(@PathVariable String userId) {
    LOGGER.info("Received request to register user: {}", userId);
    firestoreDelegate.addUser(userId);
  }

  //    @PostMapping("/login/{userId}")
  //    public void login(@PathVariable String userId) {
  //        authenticationService.loginFirebaseUser(userId);
  //    }
}
