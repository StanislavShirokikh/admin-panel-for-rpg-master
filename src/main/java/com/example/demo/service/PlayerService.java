package com.example.demo.service;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();
    Player createPlayer(PlayerDto playerDto);
    Player updatePlayer(PlayerDto playerDto, long id);
    void deletePlayer(long id);
    Player getPlayerById(long id);
}
