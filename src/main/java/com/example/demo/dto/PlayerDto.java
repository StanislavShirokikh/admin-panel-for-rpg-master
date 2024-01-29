package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlayerDto {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private LocalDateTime birthday;
    private Boolean banned;
}
