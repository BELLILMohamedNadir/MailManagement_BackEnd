package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GmailFormat {

    private long id;
    private String recipient,subject,body,path,name;
}
