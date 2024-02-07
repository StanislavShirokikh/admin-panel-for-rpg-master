package com.example.demo.filter;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
public class Filter {
    String name;
    String title;
    Race race;
    Profession profession;
    Date after;
    Date before;
    Boolean banned;
    Integer minExperience;
    Integer maxExperience;
    Integer minLevel;
    Integer maxLevel;
    PlayerOrder order;
    Integer pageNumber;
    Integer pageSize;
}
