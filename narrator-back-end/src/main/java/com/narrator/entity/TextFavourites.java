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
@Table(name = "TextFavourites")
public class TextFavourites {

    @Id
    @Column(name = "TFID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("TFID")
    private Integer tfid;

    @JsonProperty("TID")
    @Column(name = "TID", nullable = false)
    private Integer tid;

    @JsonProperty("UID")
    @Column(name = "UID", nullable = false)
    private Integer uid;

}
