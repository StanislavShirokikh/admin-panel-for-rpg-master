package com.example.demo.service;

import com.example.demo.converter.Converter;
import com.example.demo.dao.PlayerDao;
import com.example.demo.dao.repository.PlayerRepository;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;
    private final PlayerRepository playerRepository;

    @Override
    public Integer getPlayersCountByFilter(Filter filter) {
        return playerDao.getPlayersCountByFilter(filter);
    }

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = Converter.convertToPlayer(playerDto);
        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayersByFilter(Filter filter) {
        return playerDao.getPlayersByFilter(filter);
    }

    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player player = Converter.convertToPlayer(playerDto);
        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));
        return playerDao.updatePlayer(player);
    }

    @Override
    public void deletePlayer(long id) {
        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException();
        }
        playerRepository.deleteById(id);
    }

    @Override
    public Player getPlayerById(long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return player.get();
    }

    private int calculateCurrentLevel(int experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private int calculateUntilNextLevel(int level, int experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
