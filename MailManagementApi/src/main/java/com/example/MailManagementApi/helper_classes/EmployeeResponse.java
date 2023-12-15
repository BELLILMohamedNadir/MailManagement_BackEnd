package com.example.MailManagementApi.helper_classes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private long id;

    private int recuperation;

    private String name;

    private String firstName;

    private String registrationKey;

    private String structure;

    private String email;

}
