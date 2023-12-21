package com.example.demo.dao;

import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Slf4j
@RequiredArgsConstructor
public class DatabasePlayerDao implements PlayerDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simplePlayerJdbcInsert;

    @Autowired
    public DatabasePlayerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simplePlayerJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public long createPlayer(Player player) {
        return 0;
    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public void deletePlayer(long id) {

    }

    @Override
    public Player getPlayerById(long id) {
        return null;
    }
}
