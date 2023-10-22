package com.ficrowe.spotiFi.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {

  private FirebaseApp firebaseApp;

  public FirebaseConfiguration() throws IOException {
    InputStream serviceAccount = new FileInputStream("./spotifi-a965f-0fb679e55c98.json");
    GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
    FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
    firebaseApp = FirebaseApp.initializeApp(options);
  }

  @Bean
  public Firestore firestore() {
    return FirestoreClient.getFirestore(firebaseApp);
  }

  @Bean
  public FirebaseAuth firebaseAuth() {
    return FirebaseAuth.getInstance(firebaseApp);
  }
}
