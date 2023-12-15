package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Employee;
import com.example.MailManagementApi.helper_classes.EmployeeResponse;
import com.example.MailManagementApi.repository.EmployeeRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployees(long id) {
        List<Tuple> list=employeeRepository.getEmployees(id);
        List<EmployeeResponse> employees=new ArrayList<>();
        for (Tuple row : list){
            Long ids=(Long) row.get("id");
            int recuperation=(int) row.get("recuperation");
            String name=(String) row.get("name");
            String firstName=(String) row.get("first_name");
            String registrationKey=(String) row.get("registration_key");
            String structure=(String) row.get("designation_struct");
            String email=(String) row.get("email");
            employees.add(new EmployeeResponse(ids,recuperation,name,firstName,registrationKey,structure,email));
        }
        return employees;
    }

    @Override
    public void updateEmployee(long id, Employee employee) {
         employeeRepository.findById(id)
                .map(e->{
                    e.setName(employee.getName());
                    e.setFirstName(employee.getFirstName());
                    e.setRegistrationKey(employee.getRegistrationKey());
                    e.setEmail(employee.getEmail());
                    e.setStructure(employee.getStructure());
                    return employeeRepository.save(e);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void updateRecuperation(long employee_id, int recuperation) {
        int r=employeeRepository.findRecuperation(employee_id);
        r=r+recuperation;
        employeeRepository.updateRecuperation(employee_id,r);
    }

}
