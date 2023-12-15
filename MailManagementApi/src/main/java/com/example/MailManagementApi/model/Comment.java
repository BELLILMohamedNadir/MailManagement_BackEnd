package com.example.MailManagementApi.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "pdf_id",referencedColumnName = "id")
    private Mail pdf;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;
    private Date date;



}
