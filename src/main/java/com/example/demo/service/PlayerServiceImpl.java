package com.example.demo.service;

import com.example.demo.converter.Converter;
import com.example.demo.dao.PlayerDao;
import com.example.demo.dao.repository.PlayerRepository;
import com.example.demo.dao.repository.ProfessionRepository;
import com.example.demo.dao.repository.RaceRepository;
import com.example.demo.dao.repository.specification.PlayerSpecification;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.ProfessionEntity;
import com.example.demo.entity.RaceEntity;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;
    private final PlayerRepository playerRepository;
    private final RaceRepository raceRepository;
    private final ProfessionRepository professionRepository;

    @Override
    public Integer getPlayersCountByFilter(Filter filter) {
        Optional<Specification<Player>> optional = getSpecification(filter);
        Specification<Player> specification = Specification.where(null);
        if (optional.isPresent()) {
            specification = optional.get();
        }

        return Math.toIntExact(playerRepository.count(specification));
    }

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = Converter.convertToPlayer(playerDto);

        RaceEntity raceEntity = raceRepository.findByName(playerDto.getRaceDto().getName());
        player.setRaceEntity(raceEntity);

        ProfessionEntity professionEntity = professionRepository.findByName(playerDto.getProfessionDto().getName());
        player.setProfessionEntity(professionEntity);

        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));

        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayersByFilter(Filter filter) {
        Optional<Specification<Player>> specification = getSpecification(filter);
        PageRequest pageRequest = getPageRequest(filter);

        return specification.map(playerSpecification -> playerRepository.findAll(playerSpecification, pageRequest).getContent())
                .orElseGet(() -> playerRepository.findAll(pageRequest).getContent());
    }

    @Transactional
    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player player = playerRepository.findById(playerDto.getId()).orElseThrow(PlayerNotFoundException::new);
        if (playerDto.getName() != null) {
            player.setName(playerDto.getName());
        }
        if (playerDto.getTitle() != null) {
            player.setTitle(playerDto.getTitle());
        }
        if (playerDto.getRaceDto().getName() != null) {
            player.setRaceEntity(raceRepository.findByName(playerDto.getRaceDto().getName()));
        }
        if (playerDto.getProfessionDto().getName() != null) {
            player.setProfessionEntity(professionRepository.findByName(playerDto.getProfessionDto().getName()));
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
    private Optional<Specification<Player>> getSpecification(Filter filter) {
        List<Specification<Player>> specificationList = Stream.of(
                PlayerSpecification.nameLike(filter.getName()),
                PlayerSpecification.titleLike(filter.getTitle()),
                PlayerSpecification.equalsRaceName(filter.getRace()),
                PlayerSpecification.equalsProfessionName(filter.getProfession()),
                PlayerSpecification.greaterThanOrEqualToBeforeDate(filter.getBefore()),
                PlayerSpecification.lessThanOrEqualToAfterDate(filter.getAfter()),
                PlayerSpecification.equalsBanned(filter.getBanned()),
                PlayerSpecification.greaterThanOrEqualToMinExperience(filter.getMinExperience()),
                PlayerSpecification.lessThanOrEqualToMaxExperience(filter.getMaxExperience()),
                PlayerSpecification.greaterThanOrEqualToMinLevel(filter.getMinLevel()),
                PlayerSpecification.lessThanOrEqualToMaxLevel(filter.getMaxLevel()))
                .filter(Objects::nonNull)
                .toList();

        if (specificationList.isEmpty()) {
            return Optional.empty();
        }

        Specification<Player> playerSpecification = Specification.where(specificationList.get(0));
        for (int i = 1; i < specificationList.size(); i++) {
            playerSpecification = playerSpecification.and(specificationList.get(i));
        }

        return Optional.of(playerSpecification);
    }
    private PageRequest getPageRequest(Filter filter) {
        return PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.Direction.ASC,
                String.valueOf(filter.getOrder()).toLowerCase());
    }
}
