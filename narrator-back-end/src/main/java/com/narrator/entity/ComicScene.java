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
@Table(name = "ComicScene")
public class ComicScene {

    @Id
    @Column(name = "CSID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("CSID")
    private Integer csid;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @JsonProperty("CCID")
    @Column(name = "CCID", nullable = false)
    private Integer ccid;

}
