package com.example.MailManagementApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Structure {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "designation_struct")
    private String designation;
    @Column(name = "code_struct")
    private String code;
    private String motherStructure;

    public Structure(long id) {
        this.id = id;
    }
}
