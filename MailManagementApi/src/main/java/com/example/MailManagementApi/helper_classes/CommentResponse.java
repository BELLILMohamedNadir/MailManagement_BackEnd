package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private long id;
    private String comment,name,firstName,date;
    private byte[] bytes;
    private long userId;



}
