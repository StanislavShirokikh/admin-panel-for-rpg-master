package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SavePlayerDto  {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private LocalDate birthday;
    private Boolean banned;
}
