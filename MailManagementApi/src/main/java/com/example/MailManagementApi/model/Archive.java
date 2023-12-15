package com.example.MailManagementApi.model;

import com.example.MailManagementApi.helper_classes.ArchivePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Archive {


    @EmbeddedId
    private ArchivePK archivePK;

    private Boolean initialized;

    private Boolean archive;


}
