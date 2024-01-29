package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequestObject;
import com.example.demo.response.PlayerResponseObject;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
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
    public PlayerResponseObject createPlayer(@RequestBody PlayerRequestObject playerRequestObject) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequestObject);
        Player player = playerService.createPlayer(playerDto);

        return Converter.convertToPlayerResponseObject(player);
    }

    @Override
    public PlayerResponseObject updatePlayer(@PathVariable long id, @RequestBody PlayerRequestObject playerRequestObject) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequestObject);
        Player player = playerService.updatePlayer(playerDto, id);
        return Converter.convertToPlayerResponseObject(player);
    }

    @Override
    public void deletePlayer(@PathVariable @Positive long id) {
        playerService.deletePlayer(id);
    }

    @Override
    public Player getPlayerById(@PathVariable @Positive long id) {
        return playerService.getPlayerById(id);
    }
}
