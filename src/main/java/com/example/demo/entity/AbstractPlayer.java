package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractPlayer {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private Integer untilNextLevel;
    private Date birthday;
    private Boolean banned;
}
