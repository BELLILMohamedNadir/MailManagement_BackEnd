package com.example.MailManagementApi.model;

import com.example.MailManagementApi.model.Gmail;
import com.example.MailManagementApi.model.Mail;
import com.example.MailManagementApi.model.Trace;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Path {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Mail mail;
    @ManyToOne
    @JoinColumn(name = "trace_id")
    private Trace trace;

    @ManyToOne
    @JoinColumn(name = "gmail_id")
    private Gmail gmail;

    private String path;
    private String name;
    private boolean latest;

    public Path(Gmail gmail, String path, String name) {
        this.gmail = gmail;
        this.path = path;
        this.name = name;
    }

    public Path(Mail mail, Trace trace, String path, String name, boolean latest) {
        this.mail = mail;
        this.trace = trace;
        this.path = path;
        this.name = name;
        this.latest = latest;
    }
}
