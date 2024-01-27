package com.example.demo.converter;

import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.CreatePlayerRequest;
import com.example.demo.request.UpdatePlayerRequest;
import com.example.demo.response.CreateObjectResponse;
import com.example.demo.response.UpdateObjectResponse;

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
        player.setId(updatePlayerDto.getId());
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

    public static UpdatePlayerDto convertToUpdatePlayerDto(UpdatePlayerRequest updatePlayerRequest) {
        UpdatePlayerDto updatePlayerDto = new UpdatePlayerDto();
        updatePlayerDto.setName(updatePlayerRequest.getName());
        updatePlayerDto.setTitle(updatePlayerRequest.getTitle());
        updatePlayerDto.setRace(updatePlayerRequest.getRace());
        updatePlayerDto.setProfession(updatePlayerRequest.getProfession());
        updatePlayerDto.setBirthday(updatePlayerRequest.getBirthday());
        updatePlayerDto.setExperience(updatePlayerRequest.getExperience());
        updatePlayerDto.setBanned(updatePlayerRequest.getBanned());

        return updatePlayerDto;
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

    public static UpdateObjectResponse convertToUpdateObjectResponse(Player player) {
        UpdateObjectResponse updateObjectResponse = new UpdateObjectResponse();
        updateObjectResponse.setId(player.getId());
        updateObjectResponse.setName(player.getName());
        updateObjectResponse.setTitle(player.getTitle());
        updateObjectResponse.setRace(player.getRace());
        updateObjectResponse.setProfession(player.getProfession());
        updateObjectResponse.setLevel(player.getLevel());
        updateObjectResponse.setExperience(player.getExperience());
        updateObjectResponse.setUntilNextLevel(player.getUntilNextLevel());
        updateObjectResponse.setBirthday(player.getBirthday());
        updateObjectResponse.setBanned(player.getBanned());
        return updateObjectResponse;
    }


}
