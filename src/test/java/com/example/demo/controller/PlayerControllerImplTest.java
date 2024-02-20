package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.request.PlayerRequest;
import com.example.demo.response.PlayerResponse;
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

import java.util.Date;
import java.util.List;

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
    void getPlayersByFilter() throws Exception {
        Filter filter = new Filter();
        filter.setOrder(PlayerOrder.NAME);
        filter.setPageNumber(2);
        filter.setPageSize(10);
        List<Player> players = playerService.getPlayersByFilter(filter);
        List<PlayerResponse> expectedResponse = Converter.convertToListPlayerResponse(players);

        mockMvc.perform(get("/rest/players")
                .param("order", String.valueOf(PlayerOrder.NAME))
                .param("pageNumber", "2")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
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
                .andExpect(jsonPath("$.message").value("Player with this id not found"));;
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
        PlayerResponse expectedPlayer = Converter.convertToPlayerResponse(player);

        mockMvc.perform(get("/rest/players/{id}", player.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPlayer)));
    }

    @Test
    void getPlayerWithBadId() throws Exception {
        mockMvc.perform(get("/rest/players/{id}", String.valueOf(Long.MAX_VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Player with this id not found"));
    }
}