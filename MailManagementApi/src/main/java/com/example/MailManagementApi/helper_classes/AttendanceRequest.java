package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {

    private long id;
    private Employee employee;
    private String date,stat;
    private int recuperation;
    private boolean attendance;
}
