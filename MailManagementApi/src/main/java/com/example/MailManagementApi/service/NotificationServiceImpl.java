package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.NotificationMessage;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    @Lazy
    FirebaseMessaging firebaseMessaging;

    @Autowired
    @Lazy
    UserService userService;


    @Override
    public String sendNotificationByToken(NotificationMessage notificationMessage) {

        Notification notification=Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();

        List<String> tokens=userService.getTokens(notificationMessage.getRecipient());
        if (tokens!=null && !tokens.isEmpty() && firebaseMessaging!=null) {


            MulticastMessage msg = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(notification)
                    .build();
            try {
                firebaseMessaging.sendMulticast(msg);
                return "sent";
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        return "error because null";
    }

}
