package org.aibles.springsecurity.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false,unique = true)
    private String username;
    private String password;

}
