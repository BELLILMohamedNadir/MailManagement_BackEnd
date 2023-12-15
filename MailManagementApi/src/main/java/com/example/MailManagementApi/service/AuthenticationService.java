package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.AuthRequest;
import com.example.MailManagementApi.helper_classes.JwtResponse;
import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.model.User;

import java.text.ParseException;

public interface AuthenticationService {

        JwtResponse createUser(UserRequest user) throws ParseException;
        JwtResponse authenticateUser(AuthRequest signIn);

}
