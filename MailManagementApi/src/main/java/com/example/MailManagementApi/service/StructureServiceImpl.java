package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Structure;
import com.example.MailManagementApi.repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService{

    @Autowired
    StructureRepository structureRepository;

    @Override
    public void createStructure(Structure structure) {
        structureRepository.save(structure);
    }

    @Override
    public void updateStructure(long id, Structure structure) {
        structureRepository.findById(id)
                .map(s->{
                    s.setDesignation(structure.getDesignation());
                    s.setCode(structure.getCode());
                    s.setMotherStructure(structure.getMotherStructure());
                    return structureRepository.save(s);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void deleteStructure(long id) {
        structureRepository.deleteById(id);
    }

    @Override
    public List<Structure> getStructures() {
        return structureRepository.findAll();
    }

    @Override
    public List<String> getStructureDesignation() {
        return structureRepository.getStructureDesignation();
    }

    @Override
    public Structure getStructureById(long id) {
         if (structureRepository.findById(id).isPresent())
             return structureRepository.findById(id).get();
         else
             return new Structure(1,"No Structure","No Structure","No Structure");
    }

}
