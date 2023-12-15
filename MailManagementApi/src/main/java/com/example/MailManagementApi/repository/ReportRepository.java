package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Report;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {

    String Q=" SELECT * , DATE_FORMAT(date, '%M %Y') as date_to_show FROM REPORT WHERE structure_id=:id ";
    String Q_DRH=" SELECT *, DATE_FORMAT(date, '%M %Y') as date_to_showFROM REPORT WHERE approved=true || (approved=false AND structure_id=:id) ";
    String Q_FIND=" SELECT * FROM REPORT WHERE DATE(date) = CURDATE() AND structure_id=:id AND type='journalier' ";

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getReportByStructure(@Param("id") long id);

    @Query(value = Q_DRH,nativeQuery = true)
    List<Tuple> getReports(@Param("id") long id);

    @Query(value = Q_FIND,nativeQuery = true)
    Report findReport(@Param("id") long id);

}
