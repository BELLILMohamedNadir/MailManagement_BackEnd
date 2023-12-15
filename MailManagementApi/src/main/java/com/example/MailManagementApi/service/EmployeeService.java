package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Employee;
import com.example.MailManagementApi.helper_classes.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);
    List<EmployeeResponse> getEmployees(long id);
    void updateEmployee(long id,Employee employee);
    void updateRecuperation(long employee_id,int recuperation);

}
