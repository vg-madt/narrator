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
@Table(name = "ComicChapter")
public class ComicChapter {

    @Id
    @Column(name = "CCID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("CCID")
    private Integer ccid;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

   
    @JsonProperty("CID")
    @Column(name = "CID", nullable = false)
    private Integer cid;

}
