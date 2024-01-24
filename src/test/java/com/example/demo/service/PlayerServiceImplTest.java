package com.example.demo.service;

import com.example.demo.dto.SavePlayerDto;
import com.example.demo.dto.UpdatePlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class PlayerServiceImplTest {
    @Autowired
    PlayerService playerService;

    @Test
    void createPlayer() {
        SavePlayerDto savePlayerDto = new SavePlayerDto();
        savePlayerDto.setName("Ниус");
        savePlayerDto.setTitle("Приходящий Без Шума");
        savePlayerDto.setRace(Race.DWARF);
        savePlayerDto.setExperience(100);
        savePlayerDto.setProfession(Profession.DRUID);
        savePlayerDto.setBirthday(LocalDate.of(2023, 12, 21));
        savePlayerDto.setBanned(true);

        Player actualPlayer = playerService.createPlayer(savePlayerDto);
        Assertions.assertEquals(11, actualPlayer.getId());
        Assertions.assertEquals("Ниус", actualPlayer.getName());
        Assertions.assertEquals("Приходящий Без Шума", actualPlayer.getTitle());
        Assertions.assertEquals(Race.DWARF, actualPlayer.getRace());
        Assertions.assertEquals(100, actualPlayer.getExperience());
        Assertions.assertEquals(Profession.DRUID, actualPlayer.getProfession());
        Assertions.assertEquals(LocalDate.of(2023, 12, 21), actualPlayer.getBirthday());
        Assertions.assertEquals(true, actualPlayer.getBanned());
    }

    @Test
    void getPlayerById() {
        Player actualPlayer = playerService.getPlayerById(1);

        Assertions.assertEquals(1, actualPlayer.getId());
        Assertions.assertEquals("Ниус", actualPlayer.getName());
        Assertions.assertEquals("Приходящий Без Шума", actualPlayer.getTitle());
        Assertions.assertEquals(Race.HUMAN, actualPlayer.getRace());
        Assertions.assertEquals(58347, actualPlayer.getExperience());
        Assertions.assertEquals(Profession.WARRIOR, actualPlayer.getProfession());
        Assertions.assertEquals(LocalDate.of(2010, 10, 12), actualPlayer.getBirthday());
        Assertions.assertEquals(false, actualPlayer.getBanned());
    }

    @Test
    void getPlayerWithBadId() {
        Assertions.assertThrows(Exception.class, () -> playerService.getPlayerById(98));
    }

    @Test
    void updatePlayer() {
        UpdatePlayerDto updatePlayerDto = new UpdatePlayerDto();
        updatePlayerDto.setId(4L);
        updatePlayerDto.setName("Вольф");
        updatePlayerDto.setTitle("Тиран");
        updatePlayerDto.setRace(Race.HUMAN);
        updatePlayerDto.setExperience(120);
        updatePlayerDto.setProfession(Profession.DRUID);
        updatePlayerDto.setBirthday(LocalDate.of(2023, 11, 21));
        updatePlayerDto.setBanned(false);

        playerService.updatePlayer(updatePlayerDto);

        Player actualPlayer = playerService.getPlayerById(4);
        Assertions.assertEquals(4, actualPlayer.getId());
        Assertions.assertEquals("Вольф", actualPlayer.getName());
        Assertions.assertEquals("Тиран", actualPlayer.getTitle());
        Assertions.assertEquals(Race.HUMAN, actualPlayer.getRace());
        Assertions.assertEquals(120, actualPlayer.getExperience());
        Assertions.assertEquals(Profession.DRUID, actualPlayer.getProfession());
        Assertions.assertEquals(LocalDate.of(2023, 11, 21), actualPlayer.getBirthday());
        Assertions.assertEquals(false, actualPlayer.getBanned());
    }

    @Test
    void deletePlayer() {
        playerService.deletePlayer(8);
        Assertions.assertThrows(Exception.class, () -> playerService.getPlayerById(8));
    }
}