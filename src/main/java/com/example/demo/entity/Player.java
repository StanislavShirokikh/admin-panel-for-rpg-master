package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Player {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer level;
    private Integer experience;
    private Integer untilNextLevel;
    private LocalDate birthday;
    private Boolean banned;
}
