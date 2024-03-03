package com.example.demo.controller;

import com.example.demo.controller.validation.Marker;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Validated
public interface PlayerController {

    @GetMapping("/rest/players/count")
    Integer getPlayersCountByFilter(@RequestParam(required = false)  String name,
                                    @RequestParam(required = false) String title, @RequestParam(required = false) Race race,
                                    @RequestParam(required = false) Profession profession, @RequestParam(required = false) Long after,
                                    @RequestParam(required = false) Long before, @RequestParam(required = false) Boolean banned,
                                    @RequestParam(required = false) Integer minExperience, @RequestParam(required = false) Integer maxExperience,
                                    @RequestParam(required = false) Integer minLevel, @RequestParam(required = false) Integer maxLevel);

    @GetMapping("/rest/players")
    List<PlayerResponse> getPlayersByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String title,
                                            @RequestParam(required = false) Race race, @RequestParam(required = false) Profession profession,
                                            @RequestParam(required = false) Long after,
                                            @RequestParam(required = false) Long before,
                                            @RequestParam(required = false) Boolean banned, @RequestParam(required = false) Integer minExperience,
                                            @RequestParam(required = false) Integer maxExperience, @RequestParam(required = false) Integer minLevel,
                                            @RequestParam(required = false) Integer maxLevel, @RequestParam(defaultValue = "ID") PlayerOrder order,
                                            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "3") Integer pageSize);

    @Validated(Marker.OnCreate.class)
    @PostMapping("rest/players/")
    PlayerResponse createPlayer(@RequestBody @Valid PlayerRequest playerRequest);

    @Validated(Marker.OnUpdate.class)
    @PostMapping("rest/players/{id}")
    PlayerResponse updatePlayer(@PathVariable @Min(0) long id,
                                @RequestBody @Valid PlayerRequest playerRequest);

    @DeleteMapping("/rest/players/{id}")
    void deletePlayer(@PathVariable @Min(0) long id);

    @GetMapping("/rest/players/{id}")
    PlayerResponse getPlayerById(@PathVariable @Min(0) long id);
}
