package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController{
    private final PlayerService playerService;

    @Override
    public Integer getPlayersCountByFilter(String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        Filter filter = Converter.convertToFilter(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);
        return playerService.getPlayersCountByFilter(filter);
    }

    @Override
    public PlayerResponse createPlayer(PlayerRequest playerRequest) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequest);
        Player player = playerService.createPlayer(playerDto);

        return Converter.convertToPlayerResponse(player);
    }

    @Override
    public List<PlayerResponse> getPlayersByFilter(String name, String title, Race race, Profession profession,
                                                   Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience,
                                                   Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber,
                                                   Integer pageSize) {
        Filter filter = Converter.convertToFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel, order, pageNumber, pageSize);
        List<Player> players = playerService.getPlayersByFilter(filter);

        return Converter.convertToListPlayerResponse(players);
    }

    @Override
    public PlayerResponse updatePlayer(long id, PlayerRequest playerRequest) {
        PlayerDto playerDto = Converter.convertToPlayerDto(playerRequest, id);
        Player player = playerService.updatePlayer(playerDto);
        return Converter.convertToPlayerResponse(player);
    }

    @Override
    public void deletePlayer(long id) {
        playerService.deletePlayer(id);
    }

    @Override
    public PlayerResponse getPlayerById(long id) {
        Player player = playerService.getPlayerById(id);
        return Converter.convertToPlayerResponse(player);
    }
}
