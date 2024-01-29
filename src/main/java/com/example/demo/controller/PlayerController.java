package com.example.demo.controller;

import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface PlayerController {

    @GetMapping("/rest/players/count")
    List<Player> getAllPlayers();

    @PostMapping("rest/players/")
    PlayerResponse createPlayer(@RequestBody PlayerRequest playerRequest);

    @PutMapping("rest/players/{id}")
    PlayerResponse updatePlayer(@PathVariable long id, @RequestBody PlayerRequest playerRequest);

    @DeleteMapping("/rest/players/{id}")
    void deletePlayer(@PathVariable long id);

    @GetMapping("/rest/players/{id}")
    Player getPlayerById(@PathVariable long id);
}
