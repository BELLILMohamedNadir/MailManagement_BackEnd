package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Comment;
import com.example.MailManagementApi.helper_classes.CommentResponse;
import com.example.MailManagementApi.helper_classes.UserResponse;
import com.example.MailManagementApi.repository.CommentRepository;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;

    @Override
    public void addComment(Comment comment,Date date,String to,String sessionId) {
        Comment comment1=commentRepository.save(comment);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        UserResponse response=userService.getUserById(comment.getUser().getId());
        CommentResponse commentResponse=new CommentResponse(comment1.getId(), comment.getComment(), response.getName(), response.getFirstName(), simpleDateFormat.format(date),response.getBytes(),comment.getUser().getId());
        chatService.subscribeToTopic(to,sessionId,commentResponse);
    }

    @Override
    public List<CommentResponse> getComments(long id) {
        List<CommentResponse> commentResponses=new ArrayList<>();
        List<Tuple> comments= commentRepository.getCommentById(id);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        for (Tuple row : comments) {
            Long ids =(Long) row.get("id");
            String comment=(String) row.get("comment");
            String name=(String) row.get("name");
            String firstName=(String) row.get("first_name");
            String path=(String) row.get("path");
            Long userId=(Long) row.get("user_id");
            String date=simpleDateFormat.format((Date) row.get("date"));
            commentResponses.add(new CommentResponse(ids,comment,name,firstName,date,getPhoto(path),userId));
        }
        return commentResponses;
    }


    public static byte[] getPhoto(String p){
        if (p!=null)
            try {
                File file = new File(p);
                Path path = file.toPath();
                return Files.readAllBytes(path);
            } catch (IOException e) {
                // handle exception
            }
        return null;

    }
}
