package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private long structure_id;
    private String structureDesignation,structureCode,name,firstName,fun,email,beginDate,endDate,status,role,job,firebaseToken;
    boolean notification;
    private byte[] bytes;

}
