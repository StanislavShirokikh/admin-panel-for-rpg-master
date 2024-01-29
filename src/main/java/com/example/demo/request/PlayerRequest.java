package com.example.demo.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
public class PlayerRequest {

    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private LocalDate birthday;
    private Boolean banned;
    private Integer experience;
}
