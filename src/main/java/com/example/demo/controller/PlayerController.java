package com.example.demo.controller;

import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;

import java.util.List;

public interface PlayerController {
    List<Player> getAllPlayers();
    CreateObjectResponse createPlayer(CreatePlayerRequest createPlayerRequest);
    void updatePlayer(UpdatePlayerDto updatePlayerDto);
    void deletePlayer(long id);
    Player getPlayerById(long id);
}
