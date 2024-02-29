package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @Column(name = "profession_id")
    private Profession profession;
    private Integer level;
    @Column(columnDefinition = "INTEGER CHECK (0 < experience AND experience <= 10000000)")
    private Integer experience;
    @Column(name = "until_next_level", columnDefinition = "INTEGER NOT NULL")
    private Integer untilNextLevel;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    private Date birthday;
    @Column(columnDefinition = "NOT NULL")
    private Boolean banned;
}
