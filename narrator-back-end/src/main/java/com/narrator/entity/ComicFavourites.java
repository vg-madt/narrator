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
@Table(name = "ComicFavourites")
public class ComicFavourites {

    @Id
    @Column(name = "CFID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("CFID")
    private Integer cfid;

    @JsonProperty("CID")
    @Column(name = "CID", nullable = false)
    private Integer cid;

    @JsonProperty("UID")
    @Column(name = "UID", nullable = false)
    private Integer uid;

}
