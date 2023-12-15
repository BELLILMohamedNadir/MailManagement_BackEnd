package com.example.MailManagementApi.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int recuperation;

    private int recuperationToExploit;

    private String name;

    private String firstName;

    private String registrationKey;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Column(unique = true)
    private String email;

    public Employee(long id) {
        this.id = id;
    }
}
