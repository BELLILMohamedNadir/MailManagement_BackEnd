package com.example.MailManagementApi.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    private long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean expired;

    private boolean revoked;
}
