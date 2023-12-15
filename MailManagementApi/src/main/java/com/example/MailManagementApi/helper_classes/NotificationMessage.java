package com.example.MailManagementApi.helper_classes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {

    private String recipient;
    private String title;
    private String body;

}
