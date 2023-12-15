package com.example.MailManagementApi.controller;

import com.example.MailManagementApi.helper_classes.ReportResponse;
import com.example.MailManagementApi.service.AttendanceService;
import com.example.MailManagementApi.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/all/{id}")
    public List<ReportResponse> getAllReports(@PathVariable long id) throws IOException {
        return reportService.getReports(id);
    }
    @GetMapping("/{id}")
    public List<ReportResponse> getReportByStructure(@PathVariable long id){
        return reportService.getReportsByStructure(id);
    }
    @PutMapping("/update/{id}")
    public void updateApproved(@PathVariable long id){
        reportService.updateApproved(id);
    }

    @GetMapping("/daily/{id}")
    public ReportResponse getDailyReport(@PathVariable long id){
        return reportService.getDailyReports(id);
    }
}
