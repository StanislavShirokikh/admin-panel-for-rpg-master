package com.example.demo.experiments;

import com.example.demo.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SBTest {
    @Autowired
    private PlayerService playerService;
    @Test
    public void test() {
        System.out.println();
    }
}
