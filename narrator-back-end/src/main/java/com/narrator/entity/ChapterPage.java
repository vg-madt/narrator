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
@Table(name = "ChapterPage")
public class ChapterPage {

    @Id
    @Column(name = "TPID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("TPID")
    private Integer tpid;

    @Column(name = "story", nullable = false, length = 10000)
    private String story;

    @JsonProperty("TCID")
    @Column(name = "TCID", nullable = false)
    private Integer tcid;

}
