package com.narrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("UID")
    private Integer uid;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "password", nullable = false, length = 15)
    private String password;

    @Column(name = "profileImage", nullable = false)
    private String profileImage;

}
