package com.example.MailManagementApi.helper_classes;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GmailResponse {

    private long id;
    private String recipient,subject,body;
    List<String> fileName;
    @Lob
    List<byte[]> bytes;
}
