package com.narrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Token")
public class Token {

    @Id
    @Column(name = "TKID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tkid;

    @Column(name = "date", nullable = false, length = 40)
    private String date;

    @Column(name = "source", nullable = false, length = 40)
    private String source;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "UID", nullable = false)
    private Integer uid;

}
