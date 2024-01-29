package com.example.demo.controller;

import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequestObject;
import com.example.demo.response.PlayerResponseObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface PlayerController {

    @GetMapping("/rest/players/count")
    List<Player> getAllPlayers();

    @PostMapping("rest/players/")
    PlayerResponseObject createPlayer(PlayerRequestObject playerRequestObject);

    @PutMapping("rest/players/{id}")
    PlayerResponseObject updatePlayer(long id, PlayerRequestObject playerRequestObject);

    @DeleteMapping("/rest/players/")
    void deletePlayer(long id);

    @GetMapping("/rest/players/")
    Player getPlayerById(long id);
}
