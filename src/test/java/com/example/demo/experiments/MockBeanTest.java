package com.example.demo.experiments;

import com.example.demo.dao.PlayerDao;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.service.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

@SpringBootTest
public class MockBeanTest {
    @Autowired
    private PlayerServiceImpl playerService;

    @MockBean
    private PlayerDao playerDao;



    @Test
    public void test() {
        Player player = new Player();
        player.setId(777L);
        Mockito.when(playerDao.createPlayer(ArgumentMatchers.any(Player.class))).thenReturn(player);

        PlayerDto playerDto = new PlayerDto();
        playerDto.setName("name");
        playerDto.setTitle("title");
        playerDto.setRace(Race.HUMAN);
        playerDto.setProfession(Profession.DRUID);
        playerDto.setBirthday(new Date());
        playerDto.setBanned(true);
        playerDto.setExperience(123);

        Player savedPlayer = playerService.createPlayer(playerDto);

        System.out.println(savedPlayer);
    }

    @Test
    public void test2() {
        System.out.println();
    }
}
