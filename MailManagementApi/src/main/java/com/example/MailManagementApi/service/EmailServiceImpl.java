package com.example.MailManagementApi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender mailSender;

    @Transactional
    @Override
    public void sendEmail(String recipient, String title, String body, List<String> path,List<String> names) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("Gestion de courrier <gestion.courrier.app@gmail.com>");
        helper.setTo(recipient);
        helper.setSubject(title);
        helper.setText(body);

        for (int i=0;i<path.size();i++) {
            FileSystemResource file = new FileSystemResource(new File(path.get(i)));
            helper.addAttachment(names.get(i), file);
        }

        mailSender.send(message);
    }

    @Transactional
    @Override
    public void sendEmail(String recipient, String title, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Gestion de courrier <gestion.courrier.app@gmail.com>");
        message.setTo(recipient);
        message.setSubject(title);
        message.setText(body);
        mailSender.send(message);
    }

}
