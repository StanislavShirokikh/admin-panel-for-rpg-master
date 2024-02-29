package com.example.demo.request;

import com.example.demo.controller.validation.Marker;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private String race;
    @NotNull(groups = Marker.OnCreate.class)
    private String profession;
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
