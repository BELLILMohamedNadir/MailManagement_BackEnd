package com.example.MailManagementApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attendance {

    @Id
    @GeneratedValue
    private long id;

    private int recuperation;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Date date;
    private String stat;

    private boolean attendance=false;

    public Attendance(int recuperation, Employee employee, Date date, String stat, boolean attendance) {
        this.recuperation = recuperation;
        this.employee = employee;
        this.date = date;
        this.stat = stat;
        this.attendance = attendance;
    }
}
