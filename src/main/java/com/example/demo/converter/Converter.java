package com.example.demo.converter;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;

public class Converter {
    public static Player convertToPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRace(playerDto.getRace());
        player.setProfession(playerDto.getProfession());
        player.setExperience(playerDto.getExperience());
        player.setBirthday(playerDto.getBirthday());
        player.setBanned(playerDto.getBanned());
        return player;
    }
    public static PlayerDto convertToPlayerDto(PlayerRequest playerRequest, long id) {
        PlayerDto playerDto = convertToPlayerDto(playerRequest);
        playerDto.setId(id);

        return playerDto;
    }

    public static PlayerDto convertToPlayerDto(PlayerRequest playerRequest) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(playerRequest.getName());
        playerDto.setTitle(playerRequest.getTitle());
        playerDto.setRace(playerRequest.getRace());
        playerDto.setProfession(playerRequest.getProfession());
        playerDto.setBirthday(playerRequest.getBirthday());
        playerDto.setExperience(playerRequest.getExperience());
        playerDto.setBanned(playerRequest.getBanned());

        return playerDto;
    }
    public static PlayerResponse convertToPlayerResponse(Player player) {
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setId(player.getId());
        playerResponse.setName(player.getName());
        playerResponse.setTitle(player.getTitle());
        playerResponse.setRace(player.getRace());
        playerResponse.setProfession(player.getProfession());
        playerResponse.setLevel(player.getLevel());
        playerResponse.setExperience(player.getExperience());
        playerResponse.setUntilNextLevel(player.getUntilNextLevel());
        playerResponse.setBirthday(player.getBirthday().getTime());
        playerResponse.setBanned(player.getBanned());
        return playerResponse;
    }
}
