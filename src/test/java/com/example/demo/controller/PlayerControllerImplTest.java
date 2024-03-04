package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.ProfessionDto;
import com.example.demo.dto.RaceDto;
import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
import com.example.demo.service.PlayerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUpPlayers() {
        player1 = getCreatedPlayer("Юар", "Описание первого игрока",
                parseDateFromString("22.01.2023"), 1);
        player2 = getCreatedPlayer("Юг", "Описание второго игрока",
                parseDateFromString("23.01.2023"), 2);
        player3 = getCreatedPlayer("Юджин", "Описание третьего игрока",
                parseDateFromString("24.01.2023"), 3);
    }
    @AfterEach
    void removePlayers() {
        playerService.deletePlayer(player1.getId());
        playerService.deletePlayer(player2.getId());
        playerService.deletePlayer(player3.getId());
    }

    @Test
    void getPlayersCountByFilter() throws Exception {
        Filter filter = new Filter();
        filter.setRace("HUMAN");
        Integer playersCount = playerService.getPlayersCountByFilter(filter);

        mockMvc.perform(get("/rest/players/count")
                        .param("race", "HUMAN"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(playersCount)));
    }

    @Test
    void getPlayersByName() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/players")
                        .param("name", "Ю"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<PlayerResponse> actual = objectMapper.readValue(content, new TypeReference<List<PlayerResponse>>(){});
        List<PlayerResponse> sorted = getSortedPlayers(actual);
        assertEquals(sorted, actual);
    }

    @Test
    void getPlayersByTitle() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/players")
                        .param("title", "Описание"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<PlayerResponse> actual = objectMapper.readValue(content, new TypeReference<List<PlayerResponse>>(){});
        List<PlayerResponse> sorted = getSortedPlayers(actual);
        assertEquals(sorted, actual);
    }

    @Test
    void getPlayersByNameAndTitle() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber());
    }

    @Test
    void getPlayersByNameAndTitleAndRace() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", "ELF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber());
    }

    @Test
    void getPlayersByNameAndTitleAndRaceAndProfession() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", "ELF")
                        .param("profession", "CLERIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber());
    }

    @Test
    void getPlayersByNameAndTitleAndRaceAndProfessionWhenNoPlayersFound() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("title", "Описание")
                        .param("race", "ELF")
                        .param("profession", "DRUID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }
    @Test
    void getPlayersByMinAndMaxExperience() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("minExperience", String.valueOf(1))
                        .param("maxExperience", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber());
    }

    @Test
    void getPlayersByFilterWithPagination() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber());

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "2")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player2.getId()))
                .andExpect(jsonPath("$[0].name").value("Юг"))
                .andExpect(jsonPath("$[0].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(2))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber());

        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("pageNumber", "3")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(player3.getId()))
                .andExpect(jsonPath("$[0].name").value("Юджин"))
                .andExpect(jsonPath("$[0].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(3))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber());
    }

    @Test
    void getPlayersByFilterWithSorting() throws Exception {
        mockMvc.perform(get("/rest/players")
                        .param("name", "Ю")
                        .param("order", String.valueOf(PlayerOrder.NAME)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(player1.getId()))
                .andExpect(jsonPath("$[0].name").value("Юар"))
                .andExpect(jsonPath("$[0].title").value("Описание первого игрока"))
                .andExpect(jsonPath("$[0].race").value("ELF"))
                .andExpect(jsonPath("$[0].profession").value("CLERIC"))
                .andExpect(jsonPath("$[0].birthday").value(parseDateFromString("22.01.2023")))
                .andExpect(jsonPath("$[0].banned").value(true))
                .andExpect(jsonPath("$[0].experience").value(1))
                .andExpect(jsonPath("$[0].level").isNumber())
                .andExpect(jsonPath("$[0].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[1].id").value(player2.getId()))
                .andExpect(jsonPath("$[1].name").value("Юг"))
                .andExpect(jsonPath("$[1].title").value("Описание второго игрока"))
                .andExpect(jsonPath("$[1].race").value("ELF"))
                .andExpect(jsonPath("$[1].profession").value("CLERIC"))
                .andExpect(jsonPath("$[1].birthday").value(parseDateFromString("23.01.2023")))
                .andExpect(jsonPath("$[1].banned").value(true))
                .andExpect(jsonPath("$[1].experience").value(2))
                .andExpect(jsonPath("$[1].level").isNumber())
                .andExpect(jsonPath("$[1].untilNextLevel").isNumber())

                .andExpect(jsonPath("$[2].id").value(player3.getId()))
                .andExpect(jsonPath("$[2].name").value("Юджин"))
                .andExpect(jsonPath("$[2].title").value("Описание третьего игрока"))
                .andExpect(jsonPath("$[2].race").value("ELF"))
                .andExpect(jsonPath("$[2].profession").value("CLERIC"))
                .andExpect(jsonPath("$[2].birthday").value(parseDateFromString("24.01.2023")))
                .andExpect(jsonPath("$[2].banned").value(true))
                .andExpect(jsonPath("$[2].experience").value(3))
                .andExpect(jsonPath("$[2].level").isNumber())
                .andExpect(jsonPath("$[2].untilNextLevel").isNumber());
    }

    @Test
    void createPlayer() throws Exception {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Adam");
        playerRequest.setTitle("Magic");
        playerRequest.setRace("HUMAN");
        playerRequest.setProfession("DRUID");
        playerRequest.setBirthday(new Date());//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        MvcResult result = mockMvc.perform(post("/rest/players/")
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
        assertEquals(playerRequest.getRace(), player.getRace().getName());
        assertEquals(playerRequest.getProfession(), player.getProfession().getName());
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
        playerRequest.setRace("HUMAN");
        playerRequest.setProfession("DRUID");
        playerRequest.setBirthday(new Date(1332226800000L));//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players/")
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
        playerRequest.setRace("HUMAN");
        playerRequest.setProfession("DRUID");
        playerRequest.setBirthday(new Date(1332226800000L));//20.03.2012
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players/")
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
        playerRequest.setRace("HUMAN");
        playerRequest.setProfession("DRUID");
        playerRequest.setBanned(false);
        playerRequest.setExperience(1000);

        mockMvc.perform(post("/rest/players/")
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

        RaceDto raceDto = new RaceDto();
        raceDto.setName("HUMAN");
        playerDto.setRaceDto(raceDto);

        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setName("DRUID");
        playerDto.setProfessionDto(professionDto);

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
                .andExpect(jsonPath("$.race").value(playerDto.getRaceDto().getName()))
                .andExpect(jsonPath("$.profession").value(playerDto.getProfessionDto().getName()))
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
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
    }

    @Test
    void updatePlayerWhenDateIsNull() throws Exception {
        PlayerDto playerDto = getPlayerDto();

        Player player = playerService.createPlayer(playerDto);

        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setName("Scott");
        playerRequest.setTitle("Speed");
        playerRequest.setExperience(1);
        playerRequest.setRace("ELF");
        playerRequest.setProfession("CLERIC");
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
                .andExpect(jsonPath("$.level").value(0))
                .andExpect(jsonPath("$.untilNextLevel").value(99));
    }

    @Test
    void deletePlayer() throws Exception {
        PlayerDto playerDto = getPlayerDto();

        Player player = playerService.createPlayer(playerDto);

        mockMvc.perform(delete("/rest/players/{id}", player.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/rest/players/{id}", player.getId()))
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
    }
    @Test
    void getPlayerById() throws Exception {
        PlayerDto playerDto = getPlayerDto();

        Player player = playerService.createPlayer(playerDto);

        mockMvc.perform(get("/rest/players/{id}", player.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(player.getId()))
                .andExpect(jsonPath("$.name").value(player.getName()))
                .andExpect(jsonPath("$.title").value(player.getTitle()))
                .andExpect(jsonPath("$.race").value(player.getRace().getName()))
                .andExpect(jsonPath("$.profession").value(player.getProfession().getName()))
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

    private static Date parseDateFromString(String str) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private PlayerDto getPlayerDto() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("Adam");
        playerDto.setTitle("Magic");
        RaceDto raceDto = new RaceDto();
        raceDto.setName("HUMAN");
        playerDto.setRaceDto(raceDto);

        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setName("DRUID");
        playerDto.setProfessionDto(professionDto);

        playerDto.setBirthday(new Date());
        playerDto.setBanned(false);
        playerDto.setExperience(1000);
        return playerDto;
    }

    Player getCreatedPlayer(String name, String title, Date birthday,
                            int experience) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(name);
        playerDto.setTitle(title);

        RaceDto raceDto = new RaceDto();
        raceDto.setName("ELF");
        playerDto.setRaceDto(raceDto);

        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setName("CLERIC");
        playerDto.setProfessionDto(professionDto);

        playerDto.setBirthday(birthday);
        playerDto.setBanned(true);
        playerDto.setExperience(experience);

        return playerService.createPlayer(playerDto);
    }
    List<PlayerResponse> getSortedPlayers(List<PlayerResponse> list) {
        return list.stream()
                .sorted(Comparator.comparing(PlayerResponse::getId))
                .toList();
    }
}