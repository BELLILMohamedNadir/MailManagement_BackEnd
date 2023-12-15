package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceByStructure {
    private String structure;
    private long structureId;
    List<EmployeeAttendance> attendanceS;
}
