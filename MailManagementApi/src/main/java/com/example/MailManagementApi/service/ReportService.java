package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Report;
import com.example.MailManagementApi.helper_classes.ReportResponse;

import java.util.List;

public interface ReportService {
    void saveReport(Report report);
    void saveDailyReport(Report report,long id);
    List<ReportResponse> getReports(long id);
    ReportResponse getDailyReports(long id);
    List<ReportResponse> getReportsByStructure(long id);
    void updateApproved(long id);
}
