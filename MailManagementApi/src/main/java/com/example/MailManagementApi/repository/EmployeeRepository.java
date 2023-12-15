package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Employee;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    String Q="SELECT e.id,e.name,e.first_name,e.registration_key,e.recuperation,e.email,s.designation_struct " +
            "FROM EMPLOYEE e JOIN STRUCTURE s ON e.structure_id=s.id WHERE e.structure_id=:id";

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getEmployees(@Param("id") long id);

    @Query(value = "SELECT recuperation FROM EMPLOYEE WHERE id=:id",nativeQuery = true)
    int findRecuperation(@Param("id") long id);

    @Query(value = "SELECT recuperation_to_exploit FROM EMPLOYEE WHERE id=:id",nativeQuery = true)
    int findRecuperationToExploit(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE EMPLOYEE SET recuperation=:recuperation WHERE id=:id",nativeQuery = true)
    void updateRecuperation(@Param("id") long id, @Param("recuperation") int recuperation);

    @Transactional
    @Modifying
    @Query(value = "UPDATE EMPLOYEE SET recuperation_to_exploit=:recuperation WHERE id=:id",nativeQuery = true)
    void updateRecuperationToExploit(@Param("id") long id,@Param("recuperation") int r1);
}
