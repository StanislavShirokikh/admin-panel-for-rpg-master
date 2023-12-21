package com.example.demo.service;

import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;

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
}
