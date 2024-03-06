package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerDto {
    private Long id;
    private String name;
    private String title;
    private RaceDto raceDto;
    private ProfessionDto professionDto;
    private Integer experience;
    private Date birthday;
    private Boolean banned;
}
