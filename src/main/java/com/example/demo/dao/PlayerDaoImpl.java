package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.mappers.PlayerRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@ConditionalOnProperty(name = "app.storage.type", havingValue = "DATABASE")
@RequiredArgsConstructor
public class PlayerDaoImpl implements PlayerDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simplePlayerJdbcInsert;

    @Autowired
    public PlayerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simplePlayerJdbcInsert = new SimpleJdbcInsert(jdbcTemplate){{
            withTableName("player");
            usingGeneratedKeyColumns("id");
        }};
    }

    @Override
    public List<Player> getAllPlayers() {
        String sql = "SELECT player.id, player.name, player.title, race.name race_name, profession.name profession_name," +
                "player.level, player.experience, player.until_next_level, player.birthday, player.banned " +
                "FROM player " +
                "JOIN race ON player.race_id = race.id " +
                "JOIN profession ON player.profession_id = profession.id ";
        return jdbcTemplate.query(sql, new PlayerRowMapper());
    }

    @Override
    public Player createPlayer(Player player) {
        Map<String, Object> map = new HashMap<>() {{
            put("name", player.getName());
            put("title", player.getTitle());
            put("race_id", getRaceIdByName(player.getRace()));
            put("profession_id", getProfessionIdByName(player.getProfession()));
            put("level", player.getLevel());
            put("experience", player.getExperience());
            put("until_next_level", player.getUntilNextLevel());
            put("birthday", player.getBirthday());
            put("banned", player.getBanned());
        }};
        long id = simplePlayerJdbcInsert.executeAndReturnKey(map).longValue();

        String sql = "SELECT player.id, player.name, player.title, race.name race_name, profession.name profession_name," +
                "player.level, player.experience, player.until_next_level, player.birthday, player.banned " +
                "FROM player " +
                "JOIN race " +
                "ON player.race_id = race.id " +
                "JOIN profession " +
                "ON player.profession_id = profession.id " +
                "WHERE player.id=?";

        return jdbcTemplate.queryForObject(sql, new PlayerRowMapper(), id);
    }

    @Override
    public Player updatePlayer(Player player) {
        String sql = "UPDATE player SET name = ?," +
                 "title = ?, " +
                "race_id = ?," +
                "profession_id = ?," +
                "birthday = ?," +
                "banned = ?," +
                "experience = ?" +
                "WHERE id = ?";
        jdbcTemplate.update(sql, player.getName(), player.getTitle(), getRaceIdByName(player.getRace()),
                getProfessionIdByName(player.getProfession()), player.getBirthday(), player.getBanned(),
                player.getExperience(), player.getId());

        return getPlayerById(player.getId());
    }

    @Override
    public void deletePlayer(long id) {
        String sql = "DELETE from player " +
                "WHERE id=?";
        int removedLines = jdbcTemplate.update(sql, id);
        if (removedLines == 0) {
            throw new PlayerNotFoundException();
        }
    }

    @Override
    public Player getPlayerById(long id) {
        String sql = "SELECT player.id, player.name, player.title, race.name race_name, profession.name profession_name," +
                "player.level, player.experience, player.until_next_level, player.birthday, player.banned " +
                "FROM player " +
                "JOIN race ON player.race_id = race.id " +
                "JOIN profession ON player.profession_id = profession.id " +
                "WHERE player.id=?";

        Player player = null;
        try {
            player = jdbcTemplate.queryForObject(sql, new PlayerRowMapper(), id);
        } catch (Exception e) {
            throw new PlayerNotFoundException();
        }

        return player;
    }

    private Integer getRaceIdByName(Race race) {
        String sql1 = "SELECT id FROM race WHERE name=?";
        return jdbcTemplate.queryForObject(sql1, Integer.class, String.valueOf(race));
    }

    private Integer getProfessionIdByName(Profession profession) {
        String sql2 = "SELECT id FROM profession WHERE name=?";
        return jdbcTemplate.queryForObject(sql2, Integer.class, String.valueOf(profession));
    }
}
