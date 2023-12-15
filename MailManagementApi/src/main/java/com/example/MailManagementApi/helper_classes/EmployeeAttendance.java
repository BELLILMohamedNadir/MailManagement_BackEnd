package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAttendance {

    private long id ;
    private String name;
    private String firstName;
    private Date date;
    private List<String> stats;
}
