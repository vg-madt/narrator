package com.narrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name = "ComicDialog")
public class ComicDialog {
	
	@Id
    @Column(name = "CDID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("CDID")
    private Integer cdid;

	@JsonProperty("img")
    @Column(name = "imagePath", nullable = false)
    private String imagePath;

	@JsonProperty("leftM")
    @Column(name = "leftMargin", nullable = false)
    private Integer leftMargin;

	@JsonProperty("topM")
    @Column(name = "topMargin", nullable = false)
    private Integer topMargin;

    @JsonProperty("CSID")
    @Column(name = "CSID", nullable = false)
    private Integer csid;

}
