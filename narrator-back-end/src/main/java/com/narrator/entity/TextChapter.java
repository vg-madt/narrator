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
@Table(name = "TextChapter")
public class TextChapter {

    @Id
    @Column(name = "TCID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("TCID")
    private Integer tcid;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @JsonProperty("TID")
    @Column(name = "TID", nullable = false)
    private Integer tid;

}
