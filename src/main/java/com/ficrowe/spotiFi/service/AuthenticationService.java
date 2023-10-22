// package com.ficrowe.spotiFi.service;
//
// import com.ficrowe.spotiFi.delegate.FirebaseAuthDelegate;
// import com.ficrowe.spotiFi.model.AuthenticateUserRequest;
// import com.google.firebase.auth.FirebaseAuthException;
// import lombok.Getter;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// @Service
// public class AuthenticationService {
//
//
//    @Autowired
//    private FirebaseAuthDelegate firebaseAuthDelegate;
//
//    @Getter
//    private String userId;
//
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    private final static String TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";
//
//
//    public void registerFirebaseUser(AuthenticateUserRequest authenticateUserRequest) throws
// FirebaseAuthException {
//        firebaseAuthDelegate.registerUser(authenticateUserRequest.getEmail(),
// authenticateUserRequest.getPassword());
//    }
//
//    public void loginFirebaseUser(String firebaseUserId) {
//        userId = firebaseUserId;
//    }
// }
