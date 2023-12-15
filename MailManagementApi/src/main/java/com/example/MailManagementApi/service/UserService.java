package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.model.User;
import com.example.MailManagementApi.helper_classes.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface UserService {

    void createUser(UserRequest userRequest) throws ParseException;
    void updatePassword(long id, User user);
    List<UserResponse> userInfo();
    UserResponse getUserById(long id);
    void updateUserPhoto(long id, MultipartFile multipartFile);
    void updateToken(long id,String token);
    List<String> getTokens(String struct);
    void generatePassword(long id);
    void updateNotification(long id);
    void updateUserInfo(long id,UserRequest user);
}
