package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StructureRepository extends JpaRepository<Structure,Long> {

    String STRUCTURE_DESIGNATION="SELECT designation_struct FROM STRUCTURE";
    @Query(value = STRUCTURE_DESIGNATION,nativeQuery = true)
    List<String> getStructureDesignation();

    @Query(value = "SELECT id FROM STRUCTURE ",nativeQuery = true)
    List<Long> getStructuresIds();

}
