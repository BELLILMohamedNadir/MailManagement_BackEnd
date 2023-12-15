package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.Mail;
import com.example.MailManagementApi.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {

    private long id;

    private Mail pdf;


    private User user;

    private String comment;
    private String date;
}
