package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.CategoryInfo;
import com.example.MailManagementApi.helper_classes.CategoryResponse;
import com.example.MailManagementApi.model.*;

import java.util.List;

public interface CategoryService {

    void createCategory(Category category);
    void updateCategory(long id,Category category);
    List<CategoryResponse> getCategories();
    List<CategoryInfo> getArrivedCategoryInfo(long id);
    List<CategoryInfo> getSendCategoryInfo(long id);
    List<String> getCategoryDesignation(long structure_id);
    List<String> getArrivedCategoryDesignation(long structure_id);
}
