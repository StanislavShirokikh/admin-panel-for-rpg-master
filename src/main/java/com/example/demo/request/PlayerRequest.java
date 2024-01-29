package com.example.demo.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlayerRequest {
    @NotEmpty
    @Size(min = 1, max = 12)
    private String name;
    @NotEmpty
    @Size(min = 1, max = 30)
    private String title;
    @NotNull
    private Race race;
    @NotNull
    private Profession profession;
    @NotNull

    private LocalDateTime birthday;
    private Boolean banned;
    private Integer experience;
}
