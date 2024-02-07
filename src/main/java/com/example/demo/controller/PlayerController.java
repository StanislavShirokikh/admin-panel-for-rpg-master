package com.example.demo.controller;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
@Validated
public interface PlayerController {

    @GetMapping("/rest/players/count")
    Integer getPlayersCountByFilter(@RequestParam(required = false)  String name,
                                         @RequestParam(required = false) String title, @RequestParam(required = false) Race race,
                                         @RequestParam(required = false) Profession profession, @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date after,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date before, @RequestParam(required = false) Boolean banned,
                                         @RequestParam(required = false) Integer minExperience, @RequestParam(required = false) Integer maxExperience,
                                         @RequestParam(required = false) Integer minLevel, @RequestParam(required = false) Integer maxLevel);

    @GetMapping("/rest/players")
    List<PlayerResponse> getPlayersByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String title,
                                            @RequestParam(required = false) Race race, @RequestParam(required = false) Profession profession,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date after,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date before,
                                            @RequestParam(required = false) Boolean banned, @RequestParam(required = false) Integer minExperience,
                                            @RequestParam(required = false) Integer maxExperience, @RequestParam(required = false) Integer minLevel,
                                            @RequestParam(required = false) Integer maxLevel, @RequestParam(defaultValue = "ID") PlayerOrder order,
                                            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "3") Integer pageSize);

    @PostMapping("rest/players")
    PlayerResponse createPlayer(@RequestBody @Valid PlayerRequest playerRequest);

    @PutMapping("rest/players/{id}")
    PlayerResponse updatePlayer(@PathVariable @Min(0) long id,
                                @RequestBody @Valid PlayerRequest playerRequest);

    @DeleteMapping("/rest/players/{id}")
    void deletePlayer(@PathVariable @Min(0) long id);

    @GetMapping("/rest/players/{id}")
    PlayerResponse getPlayerById(@PathVariable @Min(0) long id);
}
