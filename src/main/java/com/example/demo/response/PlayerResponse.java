package com.example.demo.response;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

@Data
public class PlayerResponse {
    private Long id;
    private String name;
    private String title;
    private String race;
    private String profession;
    private Integer level;
    private Integer experience;
    private Integer untilNextLevel;
    private Long birthday;
    private Boolean banned;
}

