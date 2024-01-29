package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController{
    private final PlayerService playerService;

    @Override
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @Override
    public PlayerResponse createPlayer(PlayerRequest playerRequest) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequest);
        Player player = playerService.createPlayer(playerDto);

        return Converter.convertToPlayerResponseObject(player);
    }

    @Override
    public PlayerResponse updatePlayer(long id, PlayerRequest playerRequest) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequest, id);
        Player player = playerService.updatePlayer(playerDto);
        return Converter.convertToPlayerResponseObject(player);
    }

    @Override
    public void deletePlayer(long id) {
        playerService.deletePlayer(id);
    }

    @Override
    public Player getPlayerById(long id) {
        return playerService.getPlayerById(id);
    }
}
