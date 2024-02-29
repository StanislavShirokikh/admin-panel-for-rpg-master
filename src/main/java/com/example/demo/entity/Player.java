package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "player")
public class Player {
    @Id
    private Long id;
    @Column(columnDefinition = "VARCHAR(12) NOT NULL")
    private String name;
    @Column(columnDefinition = "VARCHAR(30) NULL")
    private String title;
    @Column(name = "race_id")
    private Race race;
    @Column(name = "profession_id")
    private Profession profession;
    @Column(columnDefinition = "INTEGER CHECK (0 < level) NOT NULL")
    private Integer level;
    @Column(columnDefinition = "INTEGER CHECK (0 < experience AND experience <= 10000000)")
    private Integer experience;
    @Column(name = "until_next_level", columnDefinition = "INTEGER NOT NULL")
    private Integer untilNextLevel;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    private Date birthday;
    @Column(columnDefinition = "NOT NULL")
    private Boolean banned;
}
