package com.example.demo.converter;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.ProfessionDto;
import com.example.demo.dto.RaceDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
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
        player.setRace(Converter.convertToRace(playerDto.getRaceDto()));
        player.setProfession(Converter.convertToProfession(playerDto.getProfessionDto()));
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

        if (playerRequest.getRace() != null) {
            RaceDto raceDto = new RaceDto();
            raceDto.setName(playerRequest.getRace().toUpperCase());
            playerDto.setRaceDto(raceDto);
        }

        if (playerRequest.getProfession() != null) {
            ProfessionDto professionDto = new ProfessionDto();
            professionDto.setName(playerRequest.getProfession().toUpperCase());
            playerDto.setProfessionDto(professionDto);
        }

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
        playerResponse.setRace(player.getRace().getName());
        playerResponse.setProfession(player.getProfession().getName());
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

    public static Filter convertToFilter(String name, String title, String race, String profession, Long after,
                                         Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                         Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        Filter filter = new Filter();
        filter.setName(name);
        filter.setTitle(title);
        if (race != null) {
            filter.setRace(race.toUpperCase());
        }
        if (profession != null) {
            filter.setProfession(profession.toUpperCase());
        }
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

    public static Race convertToRace(RaceDto raceDto) {
        Race race = new Race();
        race.setName(raceDto.getName());
        return race;
    }

    private static Profession convertToProfession(ProfessionDto professionDto) {
        Profession profession = new Profession();
        profession.setName(professionDto.getName());
        return profession;
    }
}
