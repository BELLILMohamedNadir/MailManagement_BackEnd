package com.example.MailManagementApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gmail {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String recipient,subject, body;

    public Gmail(long id) {
        this.id = id;
    }

    public Gmail(User user, String recipient, String subject, String body) {
        this.user = user;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }
}
