package com.example.MailManagementApi.service;


import com.example.MailManagementApi.helper_classes.NotificationMessage;

public interface NotificationService {
     String sendNotificationByToken(NotificationMessage notificationMessage);
}
