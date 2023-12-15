package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {

    private long id,cpt;
    private String code,designation;

}
