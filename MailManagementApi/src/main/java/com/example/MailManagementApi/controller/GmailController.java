package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.GmailDetails;
import com.example.MailManagementApi.helper_classes.GmailResponse;
import com.example.MailManagementApi.service.GmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/gmail")
@AllArgsConstructor
public class GmailController {

    @Autowired
    GmailService gmailService;

    @PostMapping("/upload")
    public void uploadPdfGmail(@RequestParam("file") List<MultipartFile> file,
                               @RequestParam("description") String detailJson) {

        ObjectMapper mapper = new ObjectMapper();
        GmailDetails detail;
        try {
            detail = mapper.readValue(detailJson, GmailDetails.class);
            gmailService.uploadPdfGmail(file,detail);
        } catch (JsonProcessingException e) {
            // Handle exception
        }

    }
    @GetMapping("/getAll/{id}")
    public List<GmailResponse> getGmail(@PathVariable("id") long id){return gmailService.getGmail(id);}

    @GetMapping("/download/email")
    public List<String> downloadEmails(){
        return gmailService.getEmails();
    }

}
