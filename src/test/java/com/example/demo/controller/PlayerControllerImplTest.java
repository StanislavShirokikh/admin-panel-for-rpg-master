package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerImplTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPlayersCountByFilter() throws Exception {
        Filter filter = new Filter();
        filter.setRace(Race.HUMAN);
        Integer playersCount = playerService.getPlayersCountByFilter(filter);

        mockMvc.perform(get("/rest/players/count")
                        .param("race", String.valueOf(Race.HUMAN)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(playersCount)));
    }

    @Test
    void getPlayersByFilter1() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter2() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("title", "Описание"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter3() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter4() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", String.valueOf(Race.ELF)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter5() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", String.valueOf(Race.ELF))
                        .param("profession", String.valueOf(Profession.CLERIC)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter6() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", String.valueOf(Race.ELF))
                        .param("profession", String.valueOf(Profession.DRUID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter7() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", String.valueOf(Race.ELF))
                        .param("profession", String.valueOf(Profession.CLERIC)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilter8() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("minExperience", String.valueOf(player1.getExperience()))
                        .param("maxExperience", String.valueOf(player3.getExperience())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilterWithPagination() throws Exception {
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()));

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "2")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player2.getId()))
                .andExpect(jsonPath("$[0].name").value(player2.getName()))
                .andExpect(jsonPath("$[0].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player2.getUntilNextLevel()));

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "3")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player3.getId()))
                .andExpect(jsonPath("$[0].name").value(player3.getName()))
                .andExpect(jsonPath("$[0].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersByFilterWithSorting() throws Exception {
        Player player3 = getCreatedPlayer("Юджин", "Описание третьего игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 3);
        Player player2 = getCreatedPlayer("Юг", "Описание второго игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("23.01.2023"), true, 2);
        Player player1 = getCreatedPlayer("Юар", "Описание первого игрока", Race.ELF, Profession.CLERIC,
                parseDateFromString("22.01.2023"), true, 1);

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("order", String.valueOf(PlayerOrder.NAME)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[0].title").value(player1.getTitle()))
                .andExpect(jsonPath("$[0].race").value(String.valueOf(player1.getRace())))
                .andExpect(jsonPath("$[0].profession").value(String.valueOf(player1.getProfession())))
                .andExpect(jsonPath("$[0].birthday").value(player1.getBirthday()))
                .andExpect(jsonPath("$[0].banned").value(player1.getBanned()))
                .andExpect(jsonPath("$[0].experience").value(player1.getExperience()))
                .andExpect(jsonPath("$[0].level").value(player1.getLevel()))
                .andExpect(jsonPath("$[0].untilNextLevel").value(player1.getUntilNextLevel()))

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()))
                .andExpect(jsonPath("$[1].title").value(player2.getTitle()))
                .andExpect(jsonPath("$[1].race").value(String.valueOf(player2.getRace())))
                .andExpect(jsonPath("$[1].profession").value(String.valueOf(player2.getProfession())))
                .andExpect(jsonPath("$[1].birthday").value(player2.getBirthday()))
                .andExpect(jsonPath("$[1].banned").value(player2.getBanned()))
                .andExpect(jsonPath("$[1].experience").value(player2.getExperience()))
                .andExpect(jsonPath("$[1].level").value(player2.getLevel()))
                .andExpect(jsonPath("$[1].untilNextLevel").value(player2.getUntilNextLevel()))

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value(player3.getName()))
                .andExpect(jsonPath("$[2].title").value(player3.getTitle()))
                .andExpect(jsonPath("$[2].race").value(String.valueOf(player3.getRace())))
                .andExpect(jsonPath("$[2].profession").value(String.valueOf(player3.getProfession())))
                .andExpect(jsonPath("$[2].birthday").value(player3.getBirthday()))
                .andExpect(jsonPath("$[2].banned").value(player3.getBanned()))
                .andExpect(jsonPath("$[2].experience").value(player3.getExperience()))
                .andExpect(jsonPath("$[2].level").value(player3.getLevel()))
                .andExpect(jsonPath("$[2].untilNextLevel").value(player3.getUntilNextLevel()));

        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void createPlayer() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Adam");
        playerRequest.setTitle("Magic");
        playerRequest.setRace(Race.HUMAN);
        playerRequest.setProfession(Profession.DRUID);
        playerRequest.setBirthday(new Date());//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        MvcResult result = mockMvc.perform(post("/rest/players")
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(playerRequest.getName()))
                .andExpect(jsonPath("$.title").value(playerRequest.getTitle()))
                .andExpect(jsonPath("$.race").value(String.valueOf(playerRequest.getRace())))
                .andExpect(jsonPath("$.profession").value(String.valueOf(playerRequest.getProfession())))
                .andExpect(jsonPath("$.birthday").value(playerRequest.getBirthday().getTime()))
                .andExpect(jsonPath("$.experience").value(playerRequest.getExperience()))
                .andExpect(jsonPath("$.banned").value(playerRequest.getBanned()))
                .andExpect(jsonPath("$.level").isNumber())
                .andExpect(jsonPath("$.untilNextLevel").isNumber())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        int level = JsonPath.read(result.getResponse().getContentAsString(), "$.level");
        int untilNextLevel = JsonPath.read(result.getResponse().getContentAsString(), "$.untilNextLevel");

        Player player = playerService.getPlayerById(id);
        assertEquals(playerRequest.getName(), player.getName());
        assertEquals(playerRequest.getTitle(), player.getTitle());
        assertEquals(playerRequest.getRace(), player.getRace());
        assertEquals(playerRequest.getProfession(), player.getProfession());
        assertEquals(playerRequest.getBirthday(), player.getBirthday());
        assertEquals(playerRequest.getBanned(), player.getBanned());
        assertEquals(playerRequest.getExperience(), player.getExperience());
        assertEquals(level, player.getLevel());
        assertEquals(untilNextLevel, player.getUntilNextLevel());
    }

    @Test
    void createPlayerWhenNameIsNull() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName(null);
        playerRequest.setTitle("Magic");
        playerRequest.setRace(Race.HUMAN);
        playerRequest.setProfession(Profession.DRUID);
        playerRequest.setBirthday(new Date(1332226800000L));//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players")
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not valid"));
    }

    @Test
    void createPlayerPlayerWhenTitleMoreThanThirtyCharacters() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Adam");
        playerRequest.setTitle("Magicdfdfgdfgdfgdfgdfgdfgdfdfgdfdfgdfgdfgd");
        playerRequest.setRace(Race.HUMAN);
        playerRequest.setProfession(Profession.DRUID);
        playerRequest.setBirthday(new Date(1332226800000L));//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players")
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not valid"));
    }

    @Test
    void createPlayerWhenDateIsNull() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Peter");
        playerRequest.setTitle("Magic");
        playerRequest.setRace(Race.HUMAN);
        playerRequest.setProfession(Profession.DRUID);
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players")
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not valid"));
    }

    @Test
    void updatePlayer() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("Adam");
        playerDto.setTitle("Magic");
        playerDto.setRace(Race.HUMAN);
        playerDto.setProfession(Profession.DRUID);
        playerDto.setBirthday(new Date(1332216800000L));//20.03.2012
        playerDto.setBanned(false);
        playerDto.setExperience(10000);

        Player player = playerService.createPlayer(playerDto);

        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Scott");

        mockMvc.perform(post("/rest/players/{id}", player.getId())
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(player.getId()))
                .andExpect(jsonPath("$.name").value(playerRequest.getName()))
                .andExpect(jsonPath("$.title").value(playerDto.getTitle()))
                .andExpect(jsonPath("$.race").value(String.valueOf(playerDto.getRace())))
                .andExpect(jsonPath("$.profession").value(String.valueOf(playerDto.getProfession())))
                .andExpect(jsonPath("$.birthday").value(playerDto.getBirthday()))
                .andExpect(jsonPath("$.experience").value(playerDto.getExperience()))
                .andExpect(jsonPath("$.banned").value(playerDto.getBanned()))
                .andExpect(jsonPath("$.level").value(player.getLevel()))
                .andExpect(jsonPath("$.untilNextLevel").value(player.getUntilNextLevel()));
    }

    @Test
    void updatePlayerWithBadId() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Scott");
        playerRequest.setBirthday(new Date());

        mockMvc.perform(post("/rest/players/{id}", String.valueOf(Long.MAX_VALUE))
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
        ;
    }

    @Test
    void updatePlayerWhenDateIsNull() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("Adam");
        playerDto.setTitle("Magic");
        playerDto.setRace(Race.HUMAN);
        playerDto.setProfession(Profession.DRUID);
        playerDto.setBirthday(new Date());
        playerDto.setBanned(false);
        playerDto.setExperience(10000);

        Player player = playerService.createPlayer(playerDto);

        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Scott");
        playerRequest.setTitle("Speed");
        playerRequest.setExperience(1000);
        playerRequest.setRace(Race.ELF);
        playerRequest.setProfession(Profession.CLERIC);
        playerRequest.setBanned(true);

        mockMvc.perform(post("/rest/players/{id}", player.getId())
                        .content(objectMapper.writeValueAsString(playerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(player.getId()))
                .andExpect(jsonPath("$.name").value(playerRequest.getName()))
                .andExpect(jsonPath("$.title").value(playerRequest.getTitle()))
                .andExpect(jsonPath("$.race").value(String.valueOf(playerRequest.getRace())))
                .andExpect(jsonPath("$.profession").value(String.valueOf(playerRequest.getProfession())))
                .andExpect(jsonPath("$.birthday").value(playerDto.getBirthday()))
                .andExpect(jsonPath("$.experience").value(playerRequest.getExperience()))
                .andExpect(jsonPath("$.banned").value(playerRequest.getBanned()))
                .andExpect(jsonPath("$.level").value(player.getLevel()))
                .andExpect(jsonPath("$.untilNextLevel").value(player.getUntilNextLevel()));
    }

    @Test
    void deletePlayer() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("Adam");
        playerDto.setTitle("Magic");
        playerDto.setRace(Race.HUMAN);
        playerDto.setProfession(Profession.DRUID);
        playerDto.setBirthday(new Date(1332216800000L));//20.03.2012
        playerDto.setBanned(false);
        playerDto.setExperience(10000);

        Player player = playerService.createPlayer(playerDto);

        mockMvc.perform(delete("/rest/players/{id}", player.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/rest/players/{id}", player.getId()))
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
    }

    @Test
    void deletePlayerWithBadId() throws Exception {
        mockMvc.perform(delete("/rest/players/{id}", String.valueOf(Long.MAX_VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Player with this id not found"));

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getPlayerById(Long.MAX_VALUE);
        });
    }

    @Test
    void getPlayerById() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("Adam");
        playerDto.setTitle("Magic");
        playerDto.setRace(Race.HUMAN);
        playerDto.setProfession(Profession.DRUID);
        playerDto.setBirthday(new Date());
        playerDto.setBanned(false);
        playerDto.setExperience(1000);

        Player player = playerService.createPlayer(playerDto);


        mockMvc.perform(get("/rest/players/{id}", player.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(player.getId()))
                .andExpect(jsonPath("$.name").value(player.getName()))
                .andExpect(jsonPath("$.title").value(player.getTitle()))
                .andExpect(jsonPath("$.race").value(String.valueOf(player.getRace())))
                .andExpect(jsonPath("$.profession").value(String.valueOf(player.getProfession())))
                .andExpect(jsonPath("$.birthday").value(player.getBirthday()))
                .andExpect(jsonPath("$.experience").value(player.getExperience()))
                .andExpect(jsonPath("$.banned").value(player.getBanned()))
                .andExpect(jsonPath("$.level").value(player.getLevel()))
                .andExpect(jsonPath("$.untilNextLevel").value(player.getUntilNextLevel()));
    }

    @Test
    void getPlayerWithBadId() throws Exception {
        mockMvc.perform(get("/rest/players/{id}", String.valueOf(Long.MAX_VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
    }

    private Date parseDateFromString(String str) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Player getCreatedPlayer(String name, String title, Race race, Profession profession, Date birthday,
                                    boolean banned, int experience) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(name);
        playerDto.setTitle(title);
        playerDto.setRace(race);
        playerDto.setProfession(profession);
        playerDto.setBirthday(birthday);
        playerDto.setBanned(banned);
        playerDto.setExperience(experience);

        return playerService.createPlayer(playerDto);
    }
}