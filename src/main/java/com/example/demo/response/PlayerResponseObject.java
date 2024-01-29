package com.example.demo.response;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerResponseObject {
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

