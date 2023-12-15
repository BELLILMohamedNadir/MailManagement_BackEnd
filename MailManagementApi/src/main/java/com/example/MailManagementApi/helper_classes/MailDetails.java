package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.Category;
import com.example.MailManagementApi.model.Structure;
import com.example.MailManagementApi.model.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDetails {
    

    private User user;

    private Structure structure;

    private List<Category> category;

    List<String> forStructure;

    List<String> internalReference;

    private String entryDate,departureDate,mailDate
            ,object,recipient,priority,type,responseOf;

    private boolean favorite,archive,response,classed,trait;



}
