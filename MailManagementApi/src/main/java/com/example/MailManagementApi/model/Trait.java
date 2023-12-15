package com.example.MailManagementApi.model;

import com.example.MailManagementApi.helper_classes.TraitPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trait {

    @EmbeddedId
    private TraitPK traitPK;

    private Boolean initialized;

    private Boolean trait;


}
