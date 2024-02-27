package com.example.demo.request;

import com.example.demo.controller.validation.Marker;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlayerRequest {
    @NotEmpty(groups = Marker.OnCreate.class)
    @Size(min = 1, max = 12, groups = Marker.OnCreate.class)
    @Size(max = 12, groups = Marker.OnUpdate.class)
    private String name;
    @NotEmpty(groups = Marker.OnCreate.class)
    @Size(min = 1, max = 30, groups = Marker.OnCreate.class)
    @Size(max = 30, groups = Marker.OnUpdate.class)
    private String title;
    @NotNull(groups = Marker.OnCreate.class)
    private Race race;
    @NotNull(groups = Marker.OnCreate.class)
    private Profession profession;
    @NotNull(groups = Marker.OnCreate.class)
    @com.example.demo.controller.validation.Date(after = "2000.01.01", before = "3000.12.31")
    private Date birthday;
    @NotNull
    private Boolean banned = false;
    @NotNull(groups = Marker.OnCreate.class)
    @Min(0)
    @Max(1000000)
    private Integer experience;
}
