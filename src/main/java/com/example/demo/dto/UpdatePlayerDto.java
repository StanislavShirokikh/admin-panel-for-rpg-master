package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UpdatePlayerDto  {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private LocalDate birthday;
    private Boolean banned;
}
