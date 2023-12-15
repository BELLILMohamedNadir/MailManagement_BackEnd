package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Report;
import com.example.MailManagementApi.helper_classes.ReportResponse;
import com.example.MailManagementApi.repository.ReportRepository;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportRepository reportRepository;

    @Autowired
            @Lazy
    DailyReportServiceGenerator dailyPdfServiceGenerator;

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public void saveDailyReport(Report report,long id) {
        Report report1=reportRepository.findReport(id);
        if (report1==null)
            reportRepository.save(report);
        else {
            report1.setPath(report.getPath());
            reportRepository.save(report1);
        }

    }


    @Override
    public List<ReportResponse> getReports(long id) {
        return reportFormat(reportRepository.getReports(id));
    }

    @Override
    public ReportResponse getDailyReports(long id) {
        try {
            return dailyPdfServiceGenerator.generate(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReportResponse> getReportsByStructure(long id) {
        return reportFormat(reportRepository.getReportByStructure(id));
    }

    @Override
    public void updateApproved(long id) {
         reportRepository.findById(id)
                .map(r->{
                    r.setApproved(true);
                    return reportRepository.save(r);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    private List<ReportResponse> reportFormat(List<Tuple> tuples){
        List<ReportResponse> reportResponses=new ArrayList<>();
        for(Tuple row:tuples){
            Long id=(Long) row.get("id");
            String path=(String) row.get("path");
            Date date=(Date) row.get("date");
            String dateToShow=(String) row.get("date_to_show");
            String type=(String) row.get("type");
            Boolean approved=(Boolean) row.get("approved");
            reportResponses.add(new ReportResponse(id,getPdfFile(path),date,dateToShow,type,approved));
        }
        return reportResponses;
    }

    public byte[] getPdfFile(String p) {
        try {
            File file = new File(p);
            Path path = file.toPath();
            byte[] pdfBytes = Files.readAllBytes(path);
            return pdfBytes;
        } catch (IOException e) {
            // handle exception
        }
        return null;
    }
}
