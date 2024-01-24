package com.example.demo.service;

import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();
    Player createPlayer(SavePlayerDto savePlayerDto);
    Player updatePlayer(UpdatePlayerDto updatePlayerDto);
    void deletePlayer(long id);
    Player getPlayerById(long id);
}
