package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Structure;
import com.example.MailManagementApi.model.User;

import java.util.List;
import java.util.Optional;

public interface StructureService {

    void createStructure(Structure structure);
    void updateStructure(long id,Structure structure);
    void deleteStructure(long id);
    List<Structure> getStructures();
    List<String> getStructureDesignation();
    Structure getStructureById(long id);
}
