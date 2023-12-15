package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.MailDetails;
import com.example.MailManagementApi.helper_classes.PathInfo;
import com.example.MailManagementApi.helper_classes.MailResponse;
import com.example.MailManagementApi.helper_classes.ReceivedMail;
import com.example.MailManagementApi.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
        @Slf4j
@RestController
@RequestMapping("/api/v1/mail")
@AllArgsConstructor
public class MailController {

    @Autowired
    private MailService mailService;


    @PostMapping("/upload")
    public void uploadMail(@RequestParam("file") List<MultipartFile> file,
                          @RequestParam("description") String detailJson) {
        ObjectMapper mapper = new ObjectMapper();
        MailDetails detail;
        try {
            detail = mapper.readValue(detailJson, MailDetails.class);
            mailService.createMail(file,detail);


        } catch (JsonProcessingException | ParseException e) {
            // Handle exception

        }


    }

    @GetMapping("/all/{id}/{userId}/{reference}")
    public List<MailResponse> allMails(@PathVariable("id") long id, @PathVariable("userId") long userId
            , @PathVariable("reference") String reference){
        return mailService.allMails(id,userId,reference);
    }

    @GetMapping("/reference/{id}")
    public List<String> references(@PathVariable long id){
        return mailService.references(id);
    }

    @GetMapping("/send/{id}/{userId}")
    public List<MailResponse> sendMails(@PathVariable("id") long id, @PathVariable("userId") long userId){
        return mailService.sendMails(id,userId);
    }

    @GetMapping("/trait/{reference}/{userId}")
    public List<MailResponse> traitMails(@PathVariable("reference") String reference, @PathVariable("userId") long userId){
        return mailService.traitMails(reference,userId);
    }

    @GetMapping("/received/{reference}/{userId}")
    public List<MailResponse> receivedMails(@PathVariable("reference") String reference, @PathVariable("userId") long userId){
        return mailService.receivedMails(reference,userId);
    }

    @GetMapping("/toTrait/{reference}/{userId}")
    public List<MailResponse> toTraitMails(@PathVariable("reference") String reference, @PathVariable("userId") long userId){
        return mailService.toTraitMails(reference,userId);
    }

    @GetMapping("/favorite/{id}/{userId}")
    public List<MailResponse> favoriteMails(@PathVariable("id") long id, @PathVariable("userId") long userId){
        return mailService.favoriteMails(id,userId);
    }

    @GetMapping("/archive/{id}/{userId}/{reference}")
    public List<MailResponse> archiveMails(@PathVariable("id") long id, @PathVariable("userId") long userId, @PathVariable("reference") String reference){
        return mailService.archiveMails(id,userId,reference);
    }

    @PutMapping("/update/{id}")
    public void updateMail(@PathVariable("id") long id,@RequestParam("updateFile") List<MultipartFile> file,
                            @RequestParam("description") String paths){

        ObjectMapper mapper = new ObjectMapper();
        try {
            PathInfo path = mapper.readValue(paths, PathInfo.class);
            mailService.updateMail(id,file,path);
        } catch (JsonProcessingException e) {
            // Handle exception
        }
    }

    @PutMapping("/update/received/{id}")
    public void updateMail(@PathVariable("id") long id,@RequestBody ReceivedMail mail){
        mailService.updateReceivedMail(id,mail);
    }

}
