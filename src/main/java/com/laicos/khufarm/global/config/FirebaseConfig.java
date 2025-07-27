package com.laicos.khufarm.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init(){
        try {
            // ClassPathResource를 사용하여 클래스패스에서 리소스를 읽어옴
            String firebaseKeyPath = "firebase/khu-farm-firebase-adminsdk-fbsvc-88f1c036b2.json";
            ClassPathResource resource = new ClassPathResource(firebaseKeyPath);

            // InputStream을 얻음
            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
