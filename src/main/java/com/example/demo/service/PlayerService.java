package com.example.demo.service;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();
    List<Player> getPlayersByFilter(Filter filter);
    Player createPlayer(PlayerDto playerDto);
    Player updatePlayer(PlayerDto playerDto);
    void deletePlayer(long id);
    Player getPlayerById(long id);
}
