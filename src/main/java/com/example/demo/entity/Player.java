package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Player {
    @Id
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer level;
    private Integer experience;
    @Column(name = "until_next_level")
    private Integer untilNextLevel;
    private Date birthday;
    private Boolean banned;
}
