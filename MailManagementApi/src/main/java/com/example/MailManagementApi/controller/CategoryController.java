package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.model.Category;
import com.example.MailManagementApi.helper_classes.CategoryInfo;
import com.example.MailManagementApi.helper_classes.CategoryResponse;
import com.example.MailManagementApi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public void createCategory(@RequestBody Category category){
         categoryService.createCategory(category);
    }

    @GetMapping("/read")
    public List<CategoryResponse> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/read/info/Arriver/{id}")
    public List<CategoryInfo> getArrivedCategoryInfo(@PathVariable("id") long id){
        return categoryService.getArrivedCategoryInfo(id);
    }
    @GetMapping("/read/info/Depart/{id}")
    public List<CategoryInfo> getSendCategoryInfo(@PathVariable("id") long id){
        return categoryService.getSendCategoryInfo(id);
    }

    @GetMapping("/read/designation/{id}")
    public List<String> getCategoryDesignation(@PathVariable("id") long structure_id){
        return categoryService.getCategoryDesignation(structure_id);
    }
    @GetMapping("/read/designation/arrived/{id}")
    public List<String> getArrivedCategoryDesignation(@PathVariable("id") long structure_id){
        return categoryService.getArrivedCategoryDesignation(structure_id);
    }
    @PutMapping("/update/{id}")
    public void updateCategory(@PathVariable long id,@RequestBody Category category){
        categoryService.updateCategory(id,category);
    }



}
