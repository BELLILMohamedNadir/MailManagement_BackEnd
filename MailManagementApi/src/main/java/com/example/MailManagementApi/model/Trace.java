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
public class Trace {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date time,updateTime;
    private String action,reference;

    public Trace(long id) {
        this.id=id;
    }

    public Trace(User user, Date time, String action, String reference) {
        this.user = user;
        this.time = time;
        this.action = action;
        this.reference = reference;
    }

    public Trace(User user, Date time, Date updateTime, String action, String reference) {
        this.user = user;
        this.time = time;
        this.updateTime = updateTime;
        this.action = action;
        this.reference = reference;
    }
}
