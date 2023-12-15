package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.ArchivePK;
import com.example.MailManagementApi.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/archive")
@Slf4j
public class ArchiveController {

    @Autowired
    ArchiveService archiveService;

    @PostMapping("/insert")
    public void insert(@RequestBody ArchivePK archive){
        archiveService.insertWhenClick(archive);
    }
    @PutMapping("/update")
    public void update(@RequestBody ArchivePK archive){
        archiveService.update(archive);
    }


}
