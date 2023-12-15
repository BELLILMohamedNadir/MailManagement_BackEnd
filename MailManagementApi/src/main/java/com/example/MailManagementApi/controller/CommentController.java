package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.CommentRequest;
import com.example.MailManagementApi.model.Comment;
import com.example.MailManagementApi.helper_classes.CommentResponse;
import com.example.MailManagementApi.repository.CommentRepository;
import com.example.MailManagementApi.service.ChatService;
import com.example.MailManagementApi.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/getComments/{id}")
    public List<CommentResponse> getComments(@PathVariable long id){ return commentService.getComments(id);}



}
