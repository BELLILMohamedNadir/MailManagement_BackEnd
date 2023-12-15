package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {

    private long structure_id;
    private Date date;
    private String stat,name,firstName,structure;
}
