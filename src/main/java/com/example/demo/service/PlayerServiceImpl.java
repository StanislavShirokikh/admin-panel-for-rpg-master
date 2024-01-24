package com.example.demo.service;

import com.example.demo.converter.Converter;
import com.example.demo.dao.PlayerDao;
import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;
    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public Player createPlayer(SavePlayerDto savePlayerDto) {
        Player player = Converter.convertToPlayer(savePlayerDto);
        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));

        return playerDao.createPlayer(player);
    }

    @Override
    public Player updatePlayer(UpdatePlayerDto updatePlayerDto) {
        Player player = Converter.convertToPlayer(updatePlayerDto);
        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));

        return playerDao.updatePlayer(player);
    }

    @Override
    public void deletePlayer(long id) {
        playerDao.deletePlayer(id);
    }

    @Override
    public Player getPlayerById(long id) {
        return playerDao.getPlayerById(id);
    }

    private int calculateCurrentLevel(int experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private int calculateUntilNextLevel(int level, int experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
