package com.example.demo.filter;

import lombok.Data;

import java.util.Date;


@Data
public class Filter {
    private String name;
    private String title;
    private String race;
    private String profession;
    private Date after;
    private Date before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;
    private PlayerOrder order;
    private Integer pageNumber;
    private Integer pageSize;
}
