package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.model.Structure;
import com.example.MailManagementApi.service.StructureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/structure")
@AllArgsConstructor
public class StructureController {

    @Autowired
    private StructureService structureService;

    @PostMapping("/create")
    public void createStructure(@RequestBody Structure structure){ structureService.createStructure(structure);}

    @GetMapping("/designation")
    public List<String> getStructureDesignation(){return structureService.getStructureDesignation();}

    @GetMapping("/getStructures")
    public List<Structure> getStructures(){ return structureService.getStructures();}

    @GetMapping("/getStructureById/{id}")
    public Structure getStructureById(@PathVariable long id){ return structureService.getStructureById(id);}

    @DeleteMapping("/delete/{id}")
    public void deleteStructure(@PathVariable long id){
        structureService.deleteStructure(id);
    }

    @PutMapping("/update/{id}")
    public void updateStructure(@PathVariable long id,@RequestBody Structure structure){structureService.updateStructure(id,structure);}


}
