package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.AttendanceRequest;
import com.example.MailManagementApi.helper_classes.ReportResponse;
import com.example.MailManagementApi.service.AttendanceService;
import com.example.MailManagementApi.service.MonthlyReportGeneratorService;
import com.example.MailManagementApi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    MonthlyReportGeneratorService monthlyReportGeneratorService;

    @Autowired
    ReportService reportService;


    @PostMapping("/insert")
    public void addAttendance(@RequestBody AttendanceRequest attendance) throws ParseException {
        attendanceService.addAttendance(attendance);
    }

    @GetMapping("/{id}")
    public boolean getAttendance(@PathVariable long id){return attendanceService.getAttendance(id);}


}
