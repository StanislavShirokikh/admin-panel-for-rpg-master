package com.example.demo.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePlayerRequest {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private LocalDate birthday;
    private Boolean banned;
    private Integer experience;
}
