package com.example.MailManagementApi.helper_classes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceResponse {

    private long id;
    private byte[] bytes;
    private Date time,updateTime;
    private String timeToShow,updateTimeToShow;
    private String action,reference,name,firstName,job,email;


}
