package com.example.demo.service;

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
public class AdministratorImpl implements AdministratorService{
    private final PlayerDao playerDao;
    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public long createPlayer(SavePlayerDto savePlayerDto) {
        return 0;
    }

    @Override
    public void updatePlayer(UpdatePlayerDto updatePlayerDto) {

    }

    @Override
    public void deletePlayer(long id) {

    }

    @Override
    public Player getPlayerById(long id) {
        return null;
    }
}
