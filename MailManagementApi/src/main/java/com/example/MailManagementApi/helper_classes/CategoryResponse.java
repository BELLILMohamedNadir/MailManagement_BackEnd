package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private long id;

    private String type,designation,code;
    private long number;
    private String designation_struct,code_struct,motherStructure;

    private long cpt;
}
