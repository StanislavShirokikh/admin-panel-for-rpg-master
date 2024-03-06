package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
    private Integer level;
    private Integer experience;
    @Column(name = "until_next_level", nullable = false)
    private Integer untilNextLevel;
    private Date birthday;
    private Boolean banned;
}
