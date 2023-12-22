package com.example.demo.converter;

import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;

public class Converter {
    public static Player convertToPlayer(SavePlayerDto savePlayerDto) {
        Player player = new Player();
        player.setName(savePlayerDto.getName());
        player.setTitle(savePlayerDto.getTitle());
        player.setRace(savePlayerDto.getRace());
        player.setProfession(savePlayerDto.getProfession());
        player.setExperience(savePlayerDto.getExperience());
        player.setBirthday(savePlayerDto.getBirthday());
        player.setBanned(savePlayerDto.getBanned());
        return player;
    }

    public static Player convertToPlayer(UpdatePlayerDto updatePlayerDto) {
        Player player = new Player();
        player.setName(updatePlayerDto.getName());
        player.setTitle(updatePlayerDto.getTitle());
        player.setRace(updatePlayerDto.getRace());
        player.setProfession(updatePlayerDto.getProfession());
        player.setExperience(updatePlayerDto.getExperience());
        player.setBirthday(updatePlayerDto.getBirthday());
        player.setBanned(updatePlayerDto.getBanned());
        return player;
    }

    public static SavePlayerDto convertToSavePlayerDto(CreatePlayerRequest createPlayerRequest) {
        SavePlayerDto savePlayerDto = new SavePlayerDto();
        savePlayerDto.setName(createPlayerRequest.getName());
        savePlayerDto.setTitle(createPlayerRequest.getTitle());
        savePlayerDto.setRace(createPlayerRequest.getRace());
        savePlayerDto.setProfession(createPlayerRequest.getProfession());
        savePlayerDto.setBirthday(createPlayerRequest.getBirthday());
        savePlayerDto.setExperience(createPlayerRequest.getExperience());
        savePlayerDto.setBanned(createPlayerRequest.getBanned());

        return savePlayerDto;
    }

    public static CreateObjectResponse convertToCreateObjectResponse(Player player) {
        CreateObjectResponse createObjectResponse = new CreateObjectResponse();
        createObjectResponse.setId(player.getId());
        createObjectResponse.setName(player.getName());
        createObjectResponse.setTitle(player.getTitle());
        createObjectResponse.setRace(player.getRace());
        createObjectResponse.setProfession(player.getProfession());
        createObjectResponse.setLevel(player.getLevel());
        createObjectResponse.setExperience(player.getExperience());
        createObjectResponse.setUntilNextLevel(player.getUntilNextLevel());
        createObjectResponse.setBirthday(player.getBirthday());
        createObjectResponse.setBanned(player.getBanned());
        return createObjectResponse;
    }
}
