package com.example.MailManagementApi.helper_classes;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse {

    private long id;

    private List<String> fileName;

    private List<String> paths;

    @Lob
    private List<byte[]> bytes;


    private String forStructure,sender,category,receivedCategory,type;

    private Date departureDate,entryDate,entryDateReceived,mailDate;

    private String entryDateToShow,entryDateReceivedToShow;

    private String internalReference,mailReference,objectReceived;

    private String object,priority,recipient,responseOf;

    private boolean classed,favorite,response,trait,archive,initializedFavorite,initializedArchive,initializedTrait;

}
