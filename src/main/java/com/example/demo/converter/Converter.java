package com.example.demo.converter;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.request.PlayerRequestObject;
import com.example.demo.response.PlayerResponseObject;

public class Converter {
    public static Player convertToPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRace(playerDto.getRace());
        player.setProfession(playerDto.getProfession());
        player.setExperience(playerDto.getExperience());
        player.setBirthday(playerDto.getBirthday());
        player.setBanned(playerDto.getBanned());
        return player;
    }
    public static PlayerDto convertToPlayerDto(PlayerRequestObject playerRequestObject) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(playerRequestObject.getName());
        playerDto.setTitle(playerRequestObject.getTitle());
        playerDto.setRace(playerRequestObject.getRace());
        playerDto.setProfession(playerRequestObject.getProfession());
        playerDto.setBirthday(playerRequestObject.getBirthday());
        playerDto.setExperience(playerRequestObject.getExperience());
        playerDto.setBanned(playerRequestObject.getBanned());

        return playerDto;
    }
    public static PlayerResponseObject convertToPlayerResponseObject(Player player) {
        PlayerResponseObject playerResponseObject = new PlayerResponseObject();
        playerResponseObject.setId(player.getId());
        playerResponseObject.setName(player.getName());
        playerResponseObject.setTitle(player.getTitle());
        playerResponseObject.setRace(player.getRace());
        playerResponseObject.setProfession(player.getProfession());
        playerResponseObject.setLevel(player.getLevel());
        playerResponseObject.setExperience(player.getExperience());
        playerResponseObject.setUntilNextLevel(player.getUntilNextLevel());
        playerResponseObject.setBirthday(player.getBirthday());
        playerResponseObject.setBanned(player.getBanned());
        return playerResponseObject;
    }
}
