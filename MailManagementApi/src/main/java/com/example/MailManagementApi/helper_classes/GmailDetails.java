package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GmailDetails {
    private User user;
    private String recipient,subject,body;
}
