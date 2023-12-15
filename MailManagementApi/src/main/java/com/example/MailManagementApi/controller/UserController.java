package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.model.User;
import com.example.MailManagementApi.helper_classes.UserResponse;
import com.example.MailManagementApi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody UserRequest userRequest) throws ParseException { userService.createUser(userRequest);
    return "user created";}

    @GetMapping("/getUsers")
    public List<UserResponse> getUsers(){ return userService.userInfo();}

    @GetMapping("/get/{id}")
    public UserResponse getUserById(@PathVariable long id){ return userService.getUserById(id);}

    @PutMapping("/update/password/{id}")
    public void updatePassword(@PathVariable long id,@RequestBody User user){userService.updatePassword(id,user);}

    @PutMapping("/generate/password/{id}")
    public void generatePassword(@PathVariable long id){userService.generatePassword(id);}

    @PutMapping("/update/token/{id}")
    public void updateToken(@PathVariable long id,@RequestBody String token){
        log.debug("user token update ");
        userService.updateToken(id,token);}

    @PutMapping("/update/notification/{id}")
    public void updateNotification(@PathVariable long id){
        userService.updateNotification(id);}

    @PutMapping("update/photo/{id}")
    public void updateUserPhoto(@PathVariable long id, @RequestParam("file") MultipartFile multipartFile){
         userService.updateUserPhoto(id,multipartFile);
    }
    @PutMapping("update/info/{id}")
    public void updateUserInfo(@PathVariable long id, @RequestBody UserRequest user){
        userService.updateUserInfo(id,user);
    }


}
