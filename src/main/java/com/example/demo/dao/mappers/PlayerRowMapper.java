package com.example.demo.dao.mappers;

import com.example.demo.entity.Player;
import com.example.demo.entity.ProfessionEntity;
import com.example.demo.entity.RaceEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PlayerRowMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setName(rs.getString("name"));
        player.setTitle(rs.getString("title"));

        RaceEntity raceEntity = new RaceEntity();
        raceEntity.setId(rs.getLong("race_id"));
        raceEntity.setName(rs.getString("race_name"));
        player.setRaceEntity(raceEntity);

        ProfessionEntity professionEntity = new ProfessionEntity();
        professionEntity.setId(rs.getLong("profession_id"));
        professionEntity.setName(rs.getString("profession_name"));
        player.setProfessionEntity(professionEntity);

        player.setLevel(rs.getInt("level"));
        player.setExperience(rs.getInt("experience"));
        player.setUntilNextLevel(rs.getInt("until_next_level"));
        player.setBirthday(new Date(rs.getTimestamp("birthday").getTime()));
        player.setBanned(rs.getBoolean("banned"));
        return player;
    }
}
