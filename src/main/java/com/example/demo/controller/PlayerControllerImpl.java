package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.request.UpdatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;
import com.example.demo.response.UpdateObjectResponse;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController{
    private final PlayerService playerService;

    @GetMapping("/rest/players/count")
    @Override
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
    @PostMapping("rest/players/")
    @Override
    public CreateObjectResponse createPlayer(@RequestBody CreatePlayerRequest createPlayerRequest) {
        if (createPlayerRequest.getBanned() == null) {
            createPlayerRequest.setBanned(false);
        }
        SavePlayerDto savePlayerDto = Converter.convertToSavePlayerDto(createPlayerRequest);
        Player player = playerService.createPlayer(savePlayerDto);

        return Converter.convertToCreateObjectResponse(player);
    }

    @PutMapping("rest/players/{id}")
    @Override
    public UpdateObjectResponse updatePlayer(@PathVariable long id, @RequestBody UpdatePlayerRequest updatePlayerRequest) {
        UpdatePlayerDto updatePlayerDto = Converter.convertToUpdatePlayerDto(updatePlayerRequest);
        updatePlayerDto.setId(id);
        Player player = playerService.updatePlayer(updatePlayerDto);
        return Converter.convertToUpdateObjectResponse(player);
    }

    @Override
    @DeleteMapping("/rest/players/")
    public void deletePlayer(@RequestParam("id") @Positive long id) {
        playerService.deletePlayer(id);
    }

    @GetMapping("/rest/players/")
    @Override
    public Player getPlayerById(@RequestParam("id") @Positive long id) {
        return playerService.getPlayerById(id);
    }
}
