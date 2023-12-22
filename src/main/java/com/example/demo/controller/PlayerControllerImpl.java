package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController{
    private final PlayerService playerService;

    @Override
    public List<Player> getAllPlayers() {
        return null;
    }
    @PostMapping("rest/players/create/")
    @Override
    public CreateObjectResponse createPlayer(CreatePlayerRequest createPlayerRequest) {
        SavePlayerDto savePlayerDto = Converter.convertToSavePlayerDto(createPlayerRequest);
        Player player = playerService.createPlayer(savePlayerDto);
        CreateObjectResponse createObjectResponse = Converter.convertToCreateObjectResponse(player);

        return createObjectResponse;
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
