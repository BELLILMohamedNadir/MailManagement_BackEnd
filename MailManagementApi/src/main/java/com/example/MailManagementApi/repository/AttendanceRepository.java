package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Attendance;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    String Q=" SELECT DATE_FORMAT(DATE_SUB(DATE_FORMAT(CURRENT_DATE, '%Y-%m-01'), INTERVAL 1 MONTH), '%Y-%m-%d') AS first_day \n" +
            "                    ,a.date,a.stat,a.employee_id,e.id,e.name,e.first_name,e.structure_id,s.designation_struct ,s.id\n" +
            "                    FROM ATTENDANCE a LEFT JOIN EMPLOYEE e \n" +
            "                    ON a.employee_id=e.id  \n" +
            "                    LEFT JOIN STRUCTURE s ON e.structure_id=s.id \n" +
            "                    WHERE \n" +
            "                    DATE(a.date) >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01') \n" +
            "                    AND DATE(a.date) < DATE_FORMAT(CURDATE(), '%Y-%m-01') \n" +
            "                    ORDER BY e.structure_id,e.id,a.date  ";


    String Q_ATTENDANCE="SELECT attendance FROM ATTENDANCE WHERE employee_id=:id ";

    String Q_DAILY=" SELECT a.date,a.stat,a.employee_id,e.id,e.name,e.first_name,e.structure_id,s.designation_struct " +
            "        FROM ATTENDANCE a LEFT JOIN EMPLOYEE e " +
            "        ON a.employee_id=e.id  " +
            "        LEFT JOIN STRUCTURE s ON e.structure_id=s.id WHERE e.structure_id=:id AND DATE(a.date) = CURDATE() ";

    String PREVIOUS_MONTH="SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%M %Y') AS month";

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getAttendances();

    @Query(value = Q_ATTENDANCE,nativeQuery = true)
    boolean getAttendance(@Param("id") long id);

    @Query(value = Q_DAILY,nativeQuery = true)
    List<Tuple> getDailyAttendance(@Param("id") long id);

    @Query(value = "SELECT * FROM Attendance WHERE Date(date)=Date(:date) AND employee_id=:id",nativeQuery = true)
        Optional<Attendance> findAttendanceByIdAndDate(@Param("id") long id,@Param("date") Date date);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ATTENDANCE SET stat=:stat , recuperation=:recuperation WHERE employee_id=:id AND Date(date)=Date(:date)",nativeQuery = true)
    void update(@Param("stat") String stat,@Param("recuperation") int recuperation,@Param("id") long id,@Param("date") Date date);

    @Query(value = PREVIOUS_MONTH,nativeQuery = true)
    String getPreviousMonth();

}
