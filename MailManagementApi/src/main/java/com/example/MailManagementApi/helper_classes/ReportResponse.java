package com.example.MailManagementApi.helper_classes;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private long id;
    @Lob
    private byte[] bytes;
    private Date date;
    private String dateToShow;
    private String type;
    private boolean approved;

}
