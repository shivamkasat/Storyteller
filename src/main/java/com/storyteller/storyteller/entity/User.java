package com.storyteller.storyteller.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"author", "id"})
@AllArgsConstructor
@Builder
public class User {
    /*
        MYSQL query to create Users table
         CREATE TABLE Users (
            UserID INT PRIMARY KEY AUTO_INCREMENT,
            Username VARCHAR(50) NOT NULL,
            PasswordHash VARCHAR(255) NOT NULL,
            Email VARCHAR(100) NOT NULL,
            DateOfBirth DATE,
            Role ENUM('Admin', 'Author', 'Reader') NOT NULL,
            DateJoined TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
    */

    public enum Role {
        Author,
        Reader,
        Admin;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "dateofbirth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "datejoined")
    private Date dateJoined;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.REMOVE})
    private Author author;


    public User(String username, String passwordHash, String email, Date dateOfBirth, Role role, Date dateJoined) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.dateJoined = dateJoined;
    }

}
