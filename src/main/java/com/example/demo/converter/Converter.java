package com.example.demo.converter;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.ProfessionDto;
import com.example.demo.dto.RaceDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.ProfessionEntity;
import com.example.demo.entity.Race;
import com.example.demo.entity.RaceEntity;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {
    public static Player convertToPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRaceEntity(Converter.convertToRace(playerDto.getRaceDto()));
        player.setProfessionEntity(Converter.convertToProfession(playerDto.getProfessionDto()));
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

        RaceDto raceDto = new RaceDto();
        raceDto.setName(playerRequest.getRace().name());
        playerDto.setRaceDto(raceDto);

        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setName(playerRequest.getProfession().name());
        playerDto.setProfessionDto(professionDto);

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
        playerResponse.setRace(player.getRaceEntity().getName());
        playerResponse.setProfession(player.getProfessionEntity().getName());
        playerResponse.setLevel(player.getLevel());
        playerResponse.setExperience(player.getExperience());
        playerResponse.setUntilNextLevel(player.getUntilNextLevel());
        playerResponse.setBirthday(player.getBirthday().getTime());
        playerResponse.setBanned(player.getBanned());
        return playerResponse;
    }

    public static List<PlayerResponse> convertToListPlayerResponse(List<Player> players) {
        List<PlayerResponse> list = new ArrayList<>();
        for (Player player : players) {
            PlayerResponse playerResponse = convertToPlayerResponse(player);
            list.add(playerResponse);
        }
        return list;
    }

    public static Filter convertToFilter(String name, String title, Race race, Profession profession, Long after,
                                         Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                         Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        Filter filter = new Filter();
        filter.setName(name);
        filter.setTitle(title);
        filter.setRace(race);
        filter.setProfession(profession);
        if (after != null && before != null) {
            filter.setAfter(new Date(after));
            filter.setBefore(new Date(before));
        }
        filter.setBanned(banned);
        filter.setMinExperience(minExperience);
        filter.setMaxExperience(maxExperience);
        filter.setMinLevel(minLevel);
        filter.setMaxLevel(maxLevel);
        filter.setOrder(order);
        filter.setPageNumber(pageNumber);
        filter.setPageSize(pageSize);
        return filter;
    }

    public static RaceEntity convertToRace(RaceDto raceDto) {
        RaceEntity raceEntity = new RaceEntity();
        raceEntity.setName(raceDto.getName());
        return raceEntity;
    }

    private static ProfessionEntity convertToProfession(ProfessionDto professionDto) {
        ProfessionEntity professionEntity = new ProfessionEntity();
        professionEntity.setName(professionDto.getName());
        return professionEntity;
    }
}
