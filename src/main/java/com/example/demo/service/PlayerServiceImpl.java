package com.example.demo.service;

import com.example.demo.converter.Converter;
import com.example.demo.dao.repository.PlayerRepository;
import com.example.demo.dao.repository.ProfessionRepository;
import com.example.demo.dao.repository.RaceRepository;
import com.example.demo.dao.repository.specification.PlayerSpecification;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final RaceRepository raceRepository;
    private final ProfessionRepository professionRepository;

    @Override
    public Integer getPlayersCountByFilter(Filter filter) {
        return (int) playerRepository.count(new PlayerSpecification(filter));
    }

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = Converter.convertToPlayer(playerDto);

        Race race = raceRepository.findByName(playerDto.getRaceDto().getName());
        player.setRace(race);

        Profession profession = professionRepository.findByName(playerDto.getProfessionDto().getName());
        player.setProfession(profession);

        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));

        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayersByFilter(Filter filter) {
        Pageable pageable = PageRequest.of(filter.getPageNumber() > 1 ? filter.getPageNumber() - 1 : 0,
                filter.getPageSize(), Sort.Direction.ASC, filter.getOrder().getFieldName());
        return playerRepository.findAll(new PlayerSpecification(filter), pageable).getContent();
    }

    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player player = playerRepository.findById(playerDto.getId()).orElseThrow(PlayerNotFoundException::new);

        if (playerDto.getName() != null) {
            player.setName(playerDto.getName());
        }
        if (playerDto.getTitle() != null) {
            player.setTitle(playerDto.getTitle());
        }
        if (playerDto.getRaceDto() != null) {
            player.setRace(raceRepository.findByName(playerDto.getRaceDto().getName()));
        }
        if (playerDto.getProfessionDto() != null) {
            player.setProfession(professionRepository.findByName(playerDto.getProfessionDto().getName()));
        }
        if (playerDto.getExperience() != null) {
            player.setExperience(playerDto.getExperience());
            player.setLevel(calculateCurrentLevel(playerDto.getExperience()));
            player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));
        }
        if (playerDto.getBirthday() != null) {
            player.setBirthday(playerDto.getBirthday());
        }
        if (playerDto.getBanned() != null) {
            player.setBanned(playerDto.getBanned());
        }

        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(long id) {
        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException();
        }
        playerRepository.deleteById(id);
    }

    @Override
    public Player getPlayerById(long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return player.get();
    }
    private int calculateCurrentLevel(int experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private int calculateUntilNextLevel(int level, int experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
