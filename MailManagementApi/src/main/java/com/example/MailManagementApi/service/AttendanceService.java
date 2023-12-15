package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.AttendanceRequest;

import java.text.ParseException;

public interface AttendanceService {

    void addAttendance(AttendanceRequest attendance) throws ParseException;
    boolean getAttendance(long id);
    void insertAttendance();
}
