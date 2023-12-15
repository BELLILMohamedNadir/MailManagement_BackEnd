package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.AuthRequest;
import com.example.MailManagementApi.helper_classes.JwtResponse;
import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.model.User;
import com.example.MailManagementApi.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> auth(@RequestBody AuthRequest sign){
        return ResponseEntity.ok(authenticationService.authenticateUser(sign));
    }

//    @PostMapping("/register")
//        public ResponseEntity<JwtResponse> createUser(@RequestBody UserRequest user) throws ParseException {
//        return ResponseEntity.ok(authenticationService.createUser(user));
//    }
}
