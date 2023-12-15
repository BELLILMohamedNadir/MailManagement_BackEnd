package com.example.MailManagementApi.model;

import com.example.MailManagementApi.helper_classes.FavoritePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite {

    @EmbeddedId
    private FavoritePK favoritePK;

    private Boolean initialized;

    private Boolean favorite;

}
