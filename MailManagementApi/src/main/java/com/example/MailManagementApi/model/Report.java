package com.example.MailManagementApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    private String path;

    private Date date;

    private boolean approved;

    private String type;

    public Report(Structure structure, String path, Date date, boolean approved, String type) {
        this.structure = structure;
        this.path = path;
        this.date = date;
        this.approved = approved;
        this.type = type;
    }
}
