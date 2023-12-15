package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.Email;
import com.example.MailManagementApi.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@AllArgsConstructor
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody Email email){
        emailService.sendEmail(email.getRecipient(),email.getTitle(),email.getBody());
    }

}
