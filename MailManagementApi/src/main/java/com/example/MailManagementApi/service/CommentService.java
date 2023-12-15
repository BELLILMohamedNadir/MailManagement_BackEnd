package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.CommentResponse;
import com.example.MailManagementApi.model.*;

import java.util.Date;
import java.util.List;

public interface CommentService {

    void addComment(Comment comment,Date date,String to,String sessionId);
    List<CommentResponse> getComments(long id);

}
