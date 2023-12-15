package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Attendance;
import com.example.MailManagementApi.helper_classes.AttendanceRequest;
import com.example.MailManagementApi.model.Employee;
import com.example.MailManagementApi.repository.AttendanceRepository;
import com.example.MailManagementApi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    EmployeeRepository employeeRepository;



    @Transactional
    @Override
    public void addAttendance(AttendanceRequest attendance) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Optional<Attendance> attendance1=attendanceRepository.findAttendanceByIdAndDate(attendance.getEmployee().getId(),simpleDateFormat.parse(attendance.getDate()));

        if (attendance1.isPresent()){
            if (attendance.getStat().trim().equals("RÉCUPÉRATION")) {

                int r1 = employeeRepository.findRecuperationToExploit(attendance.getEmployee().getId());
                int r = employeeRepository.findRecuperation(attendance.getEmployee().getId());
                r = r - attendance.getRecuperation();
                // with recuperationToExploit we can manage the recuperation every midnight ( scheduled task )
                // the recuperation field to know how much recuperation the employee has
                r1=r1+attendance.getRecuperation();
                employeeRepository.updateRecuperation(attendance.getEmployee().getId(), r);
                employeeRepository.updateRecuperationToExploit(attendance.getEmployee().getId(),r1);
                //if the attendance is already RÉCUPÉRATION in this day we're going to update the number of recuperationToExploit
                if (attendance1.get().getStat().equals("RÉCUPÉRATION")){
                    attendanceRepository.update(attendance.getStat(),attendance.getRecuperation()+attendance1.get().getRecuperation(),attendance.getEmployee().getId(),simpleDateFormat.parse(attendance.getDate()));
                }else{
                    attendanceRepository.update(attendance.getStat(),attendance.getRecuperation(),attendance.getEmployee().getId(),simpleDateFormat.parse(attendance.getDate()));
                }
            }else{
                if (!attendance1.get().getStat().equals("RÉCUPÉRATION"))
                    attendanceRepository.update(attendance.getStat(),attendance.getRecuperation(),attendance.getEmployee().getId(),simpleDateFormat.parse(attendance.getDate()));
            }

        }else{
            attendanceRepository.save(new Attendance(attendance.getRecuperation(), attendance.getEmployee(),simpleDateFormat.parse(attendance.getDate()),attendance.getStat(),true));
        }


    }


    @Override
    public boolean getAttendance(long id) {
        return attendanceRepository.getAttendance(id);
    }


    @Scheduled(cron = "0 10 0 * * *")
    public void insertAttendance(){
        List<Employee> employees=employeeRepository.findAll();
        for (Employee employee:employees){
            if (employee.getRecuperationToExploit()==0) {
                Attendance attendance = new Attendance(0, new Employee(employee.getId()), Calendar.getInstance().getTime(), "PRÉSENT", true);
                attendanceRepository.save(attendance);
            }else{
                employee.setRecuperationToExploit(employee.getRecuperationToExploit()-1);
                employeeRepository.updateRecuperationToExploit(employee.getId(),employee.getRecuperationToExploit());
                Attendance attendance = new Attendance(employee.getRecuperationToExploit(), new Employee(employee.getId()), Calendar.getInstance().getTime(), "RÉCUPÉRATION", true);
                attendanceRepository.save(attendance);
            }
        }

    }

//    @Scheduled(cron = "0 15 0 * * *")
//    public void sendEmailToAbsence(){
//
//    }


}
