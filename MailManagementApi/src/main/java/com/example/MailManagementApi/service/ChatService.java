package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.CommentResponse;

public interface ChatService {
    void subscribeToTopic(String variable,String subscriberId,CommentResponse commentResponse);
    void unsubscribeFromTopic(String subscriberId);
    void sendToGroup(String variable, CommentResponse chatMessage);
}
