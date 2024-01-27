package com.example.demo.controller;

import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.request.UpdatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;
import com.example.demo.response.UpdateObjectResponse;

import java.util.List;

public interface PlayerController {
    List<Player> getAllPlayers();
    CreateObjectResponse createPlayer(CreatePlayerRequest createPlayerRequest);
    UpdateObjectResponse updatePlayer(long id, UpdatePlayerRequest updatePlayerRequest);
    void deletePlayer(long id);
    Player getPlayerById(long id);
}
