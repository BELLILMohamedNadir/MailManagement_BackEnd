package com.example.MailManagementApi.helper_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailFormat {

    private long id;

    private String forStructure,sender,type,category,receivedCategory;
    private Date departureDate,entryDate,entryDateReceived,mailDate;
    private String entryDateToShow,entryDateReceivedToShow;
    private String internalReference,mailReference,objectReceived;

    private String name,path,object,priority,recipient,responseOf;

    private boolean classed,favorite,response,trait,archive,initializedFavorite,initializedArchive,initializedTrait;

}
