package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.Structure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private long id;

    private Structure structure;

    private String name;
    private String firstName;
    private String fun;
    private String email;
    private String role;
    private String beginDate;
    private String endDate;
    private String status;
    private String password;
    private String job;
    private String path;
    private String firebaseToken;
    private boolean notification;


}
