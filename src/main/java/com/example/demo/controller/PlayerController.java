package com.example.demo.controller;

import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Validated
public interface PlayerController {

    @GetMapping("/rest/players/count")
    List<Player> getAllPlayers();

    @PostMapping("rest/players/")
    PlayerResponse createPlayer(@RequestBody @Valid PlayerRequest playerRequest);

    @PutMapping("rest/players/{id}")
    PlayerResponse updatePlayer(@PathVariable @Min(0) long id,
                                @RequestBody @Valid PlayerRequest playerRequest);

    @DeleteMapping("/rest/players/{id}")
    void deletePlayer(@PathVariable @Min(0) long id);

    @GetMapping("/rest/players/{id}")
    PlayerResponse getPlayerById(@PathVariable @Min(0) long id);
}
