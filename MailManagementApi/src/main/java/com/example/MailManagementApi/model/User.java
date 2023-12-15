package com.example.MailManagementApi.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "structure_id",referencedColumnName = "id")
    private Structure structure;

    private String name;
    private String firstName;
    private String fun;
    @Column(unique = true)
    private String email;
    private String role;
    private Date beginDate;
    private Date endDate;
    private String status;
    private String password;
    private String job;
    private String path;
    private String firebaseToken;
    private boolean notification;


    public User(long id) {
        this.id = id;
    }


    public User(Structure structure, String name, String firstName, String fun, String email, String role, Date beginDate, Date endDate, String status, String password, String job, String path, String firebaseToken) {
        this.structure = structure;
        this.name = name;
        this.firstName = firstName;
        this.fun = fun;
        this.email = email;
        this.role = role;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.status = status;
        this.password = password;
        this.job = job;
        this.path = path;
        this.firebaseToken = firebaseToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
