package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.GmailDetails;
import com.example.MailManagementApi.helper_classes.GmailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GmailService {
    void uploadPdfGmail(List<MultipartFile> file, GmailDetails detail);
    List<GmailResponse> getGmail(long id);
    List<String> getEmails();
}
