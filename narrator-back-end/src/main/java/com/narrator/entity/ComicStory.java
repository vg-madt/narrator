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
@Table(name = "ComicStory")
public class ComicStory {

    @Id
    @Column(name = "CID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("CID")
    private Integer cid;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "coverImage", nullable = false)
    private String coverImage;

    @Column(name = "genre", nullable = false, length = 20)
    private String genre;

    @JsonProperty("UID")
    @Column(name = "UID", nullable = false)
    private Integer uid;

}
