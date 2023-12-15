package com.example.MailManagementApi.service;

import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    void sendEmail(String recipient, String title, String body, List<String> path, List<String> names) throws MessagingException;
    void sendEmail(String recipient,String title,String body);
}
