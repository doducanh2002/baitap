package org.aibles.authentication.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "username", unique = true, nullable = false)
  @NotBlank
  private String username;

  @Column(name = "password", nullable = false)
  @NotBlank
  private String password;

  @Column(name = "email")
  @NotBlank
  private String email;

}
