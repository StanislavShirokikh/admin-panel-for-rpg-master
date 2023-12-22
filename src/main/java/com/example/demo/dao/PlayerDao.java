package com.example.demo.dao;

import com.example.demo.entity.Player;

import java.util.List;

public interface PlayerDao {
    List<Player> getAllPlayers();
    Player createPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(long id);
    Player getPlayerById(long id);

}
