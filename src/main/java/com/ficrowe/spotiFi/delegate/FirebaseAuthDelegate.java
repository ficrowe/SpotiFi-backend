// package com.ficrowe.spotiFi.delegate;
//
// import com.ficrowe.spotiFi.model.firestore.FirestoreUser;
// import com.google.cloud.firestore.Firestore;
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseAuthException;
// import com.google.firebase.auth.UserRecord;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// @Service
// public class FirebaseAuthDelegate {
//
//    @Autowired
//    private FirebaseAuth firebaseAuth;
//
//    @Autowired
//    private FirestoreDelegate firestoreDelegate;
//
//    public String registerUser(String email, String password) throws FirebaseAuthException {
//        UserRecord.CreateRequest createUserRequest = new UserRecord.CreateRequest();
//        createUserRequest.setEmail(email);
//        createUserRequest.setPassword(password);
//        UserRecord user = firebaseAuth.createUser(createUserRequest);
//
//        firestoreDelegate.addUser(user.getUid());
//
//        return user.getUid();
//    }
//
//    public String loginUser(String userId) {
////        UserRecord user = firebaseAuth.getUserByEmail(email);
////        return user.getUid();
//        return userId;
//    }
// }
