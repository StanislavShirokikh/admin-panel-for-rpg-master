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
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@Entity
@Table(name = "player")
@DynamicUpdate
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private RaceEntity raceEntity;
    @ManyToOne
    @JoinColumn(name = "profession_id", nullable = false)
    private ProfessionEntity professionEntity;
    @Column(nullable = false)
    private Integer level;
    @Column(nullable = false)
    private Integer experience;
    @Column(name = "until_next_level", nullable = false)
    private Integer untilNextLevel;
    @Column(nullable = false)
    private Date birthday;
    @Column(nullable = false)
    private Boolean banned;
}
