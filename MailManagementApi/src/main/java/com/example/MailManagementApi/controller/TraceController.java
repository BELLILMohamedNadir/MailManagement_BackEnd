package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.TraceResponse;
import com.example.MailManagementApi.service.TraceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trace")
@AllArgsConstructor
public class TraceController {

    @Autowired
    private TraceService traceService;

    @GetMapping("/read")
    public List<TraceResponse> getTrace(){
        return traceService.get();
    }


}
