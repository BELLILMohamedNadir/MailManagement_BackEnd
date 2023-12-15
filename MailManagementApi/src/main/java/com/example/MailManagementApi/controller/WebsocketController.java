package com.example.MailManagementApi.controller;

import com.example.MailManagementApi.helper_classes.CommentRequest;
import com.example.MailManagementApi.model.Comment;
import com.example.MailManagementApi.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/websocket")
@AllArgsConstructor
@Slf4j
public class WebsocketController {


    @Autowired
    private CommentService commentService;

    @MessageMapping("/websocket.connect/{to}")
    public void connect(SimpMessageHeaderAccessor headerAccessor,@DestinationVariable String to, @Payload CommentRequest comment){

        Date date= Calendar.getInstance().getTime();
        Comment c=new Comment(comment.getId(),comment.getPdf(),comment.getUser(),comment.getComment(),date);
        commentService.addComment(c,date,to,headerAccessor.getSessionId());
    }
}
