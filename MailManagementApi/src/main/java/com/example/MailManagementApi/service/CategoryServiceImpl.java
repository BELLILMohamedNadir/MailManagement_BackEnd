package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.helper_classes.CategoryInfo;
import com.example.MailManagementApi.helper_classes.CategoryResponse;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.CategoryRepository;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void createCategory(Category category) {
        category.setCpt(category.getNumber());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(long id, Category category) {
        if (category.getDesignation()!=null && category.getCode()!=null){
            categoryRepository.findById(id)
                    .map(c->{
                        c.setDesignation(category.getDesignation());
                        c.setCode(category.getCode());
                        return categoryRepository.save(c);
                    }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
        }
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Tuple> resultList = categoryRepository.getCategories();
        List<CategoryResponse> list=new ArrayList<>();
        for (Tuple row : resultList) {
            Long id = (Long) row.get("id");
            String code = (String) row.get("code");
            String type = (String) row.get("type");
            String designation = (String) row.get("designation");
            Long number = (Long) row.get("number");
            String designation_struct = (String) row.get("designation_struct");
            String code_struct = (String) row.get("code_struct");
            String motherStructure = (String) row.get("mother_structure");
            Long cpt=(Long) row.get("cpt");
            CategoryResponse categoryResponse=new CategoryResponse(id,type,designation,code,number,designation_struct
                    ,code_struct,motherStructure,cpt);
            list.add(categoryResponse);
        }

        return list;
    }

    
    @Override
    public List<CategoryInfo> getArrivedCategoryInfo(long id) {
        List<Tuple> tuple=categoryRepository.getArrivedCategoryInfo(id);
        List<CategoryInfo> categoryInfo=new ArrayList<>();
        for (Tuple row : tuple) {
            Long Id=(Long) row.get("id");
            Long cpt = (Long) row.get("cpt");
            String code=(String) row.get("code");
            String designation=(String) row.get("designation");
            categoryInfo.add(new CategoryInfo(Id,cpt,code,designation));
        }
        return categoryInfo;
    }
    @Override
    public List<CategoryInfo> getSendCategoryInfo(long id) {
        List<Tuple> tuple=categoryRepository.getSendCategoryInfo(id);
        List<CategoryInfo> categoryInfo=new ArrayList<>();
        for (Tuple row : tuple) {
            Long Id=(Long) row.get("id");
            Long cpt = (Long) row.get("cpt");
            String code=(String) row.get("code");
            String designation=(String) row.get("designation");
            categoryInfo.add(new CategoryInfo(Id,cpt,code,designation));
        }
        return categoryInfo;
    }

    @Override
    public List<String> getCategoryDesignation(long structure_id) {
        return categoryRepository.getCategoryDesignation(structure_id);
    }

    @Override
    public List<String> getArrivedCategoryDesignation(long structure_id) {
        return categoryRepository.getArrivedCategoryDesignation(structure_id);
    }
}
