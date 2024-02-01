package com.example.demo.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Min(946684800000L)
    @Max(325036800_000_000L)
    private Long birthday;
    @NotNull
    private Boolean banned = false;
    @NotNull
    @Min(0)
    @Max(1000000)
    private Integer experience;
}
