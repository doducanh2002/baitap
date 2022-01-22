package com.example.springsecurity.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;
    private String password;
}
