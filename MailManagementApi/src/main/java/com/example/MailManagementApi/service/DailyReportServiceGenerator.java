package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.ReportResponse;

import java.io.IOException;

public interface DailyReportServiceGenerator {
    ReportResponse generate(long structure_id) throws IOException;
}
