package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.TraitPK;
import com.example.MailManagementApi.service.TraitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trait")
@Slf4j
public class TraitController {

    @Autowired
    TraitService traitService;

    @PostMapping("/insert")
    public void insert(@RequestBody TraitPK trait){
        traitService.insert(trait);
    }


}
