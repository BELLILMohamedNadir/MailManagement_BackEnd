package com.example.MailManagementApi;

import com.example.MailManagementApi.service.EmailService;
import com.example.MailManagementApi.service.NotificationService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class MailManagementApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MailManagementApiApplication.class, args);
    }


    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials=GoogleCredentials
                .fromStream(new ClassPathResource("path of fireBase file").getInputStream());

        FirebaseOptions firebaseOptions=FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp firebaseApp=FirebaseApp.initializeApp(firebaseOptions,"mail_management");
        return FirebaseMessaging.getInstance(firebaseApp);
    }



    @Override
    public void run(String... args){

    }

}

