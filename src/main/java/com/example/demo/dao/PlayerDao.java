package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;

import java.util.List;

public interface PlayerDao {
    Integer getPlayersCountByFilter(Filter filter);
    List<Player> getPlayersByFilter(Filter filter);
    Player createPlayer(Player player);
    Player updatePlayer(Player player);
    void deletePlayer(long id);
    Player getPlayerById(long id);

}
