package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.model.Employee;
import com.example.MailManagementApi.helper_classes.EmployeeResponse;
import com.example.MailManagementApi.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public void addEmployee(@RequestBody Employee employee){
         employeeService.addEmployee(employee);
    }

    @GetMapping("/read/{id}")
    public List<EmployeeResponse> getEmployees(@PathVariable long id){
        return employeeService.getEmployees(id);
    }


    @PutMapping("/update/{id}")
    public void updateEmployee(@PathVariable long id,@RequestBody Employee employee){
        employeeService.updateEmployee(id,employee);
    }

    @PutMapping("/update/recuperation/{id}")
    public void updateRecuperation(@PathVariable long id,@RequestBody int recuperation){
        employeeService.updateRecuperation(id,recuperation);
    }

}