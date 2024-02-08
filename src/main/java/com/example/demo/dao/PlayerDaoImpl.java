package com.example.demo.dao;

import com.example.demo.dao.mappers.PlayerRowMapper;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Repository
@Slf4j
@ConditionalOnProperty(name = "app.storage.type", havingValue = "DATABASE")
public class PlayerDaoImpl implements PlayerDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simplePlayerJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simplePlayerJdbcInsert = new SimpleJdbcInsert(jdbcTemplate){{
            withTableName("player");
            usingGeneratedKeyColumns("id");
        }};
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Integer getPlayersCountByFilter(Filter filter) {
        List<Player> players = getPlayersListWithSqlOption(filter);

        return players.size();
    }

    @Override
    public List<Player> getPlayersByFilter(Filter filter) {
        return getPlayersListWithSqlOption(filter);
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
        String sql = "UPDATE player SET ";

        List<String> clauses = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        clauses.add("name = ?");
        values.add(player.getName());
        if (player.getTitle() != null) {
            clauses.add("title = ?");
            values.add(player.getTitle());
        }
        if (player.getRace() != null) {
            clauses.add("race_id = (SELECT id FROM race WHERE name = ?)");
            values.add(player.getRace().name());
        }
        if (player.getProfession() != null) {
            clauses.add("profession_id = (SELECT id FROM profession WHERE name = ?)");
            values.add(player.getProfession().name());
        }
        if (player.getBirthday() != null) {
            clauses.add("birthday = ?");
            values.add(player.getBirthday());
        }

        if (player.getBanned() != null) {
            clauses.add("banned = ?");
            values.add(player.getBanned());
        }
        if (player.getExperience() != null) {
            clauses.add("experience = ?");
            values.add(player.getExperience());
        }

        StringJoiner joiner = new StringJoiner(", ");
        clauses.forEach(joiner::add);

        sql += joiner.toString();
        sql += " WHERE id = ?";

        values.add(player.getId());
        log.debug("Запрос на обновление: {}", sql);
        jdbcTemplate.update(sql, values.toArray());

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
            log.error(e.getMessage());
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

    private List<Player> getPlayersListWithSqlOption(Filter filter) {
        List<String> queryConditions = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filter.getName() != null) {
            queryConditions.add("player.name LIKE :name");
            params.addValue("name", "%" + filter.getName() + "%");
        }
        if (filter.getTitle() != null) {
            queryConditions.add("player.title LIKE :title");
            params.addValue("title", "%" + filter.getTitle() + "%");
        }
        if (filter.getRace() != null) {
            queryConditions.add("race.name = :race_name ");
            params.addValue("race_name", String.valueOf(filter.getRace()));
        }
        if (filter.getProfession() != null) {
            queryConditions.add("profession.name = :profession_name");
            params.addValue("profession_name", String.valueOf(filter.getProfession()));
        }
        if (filter.getAfter() != null) {
            queryConditions.add("player.birthday > :after");
            params.addValue("after", filter.getAfter());
        }
        if (filter.getBefore() != null) {
            queryConditions.add("player.birthday < :before");
            params.addValue("before", filter.getBefore());
        }
        if (filter.getBanned() != null) {
            queryConditions.add("player.banned = :banned");
            params.addValue("banned", filter.getBanned());
        }
        if (filter.getMinExperience() != null) {
            queryConditions.add("player.experience > :minExperience");
            params.addValue("minExperience", filter.getMinExperience());
        }
        if (filter.getMaxExperience() != null) {
            queryConditions.add("player.experience < :maxExperience");
            params.addValue("maxExperience", filter.getMaxExperience());
        }
        if (filter.getMinLevel() != null) {
            queryConditions.add("player.level > :minLevel");
            params.addValue("minLevel", filter.getMinLevel());
        }
        if (filter.getMaxLevel() != null) {
            queryConditions.add("player.level < :maxLevel");
            params.addValue("maxLevel", filter.getMaxLevel());
        }

        String sqlCondition = "";
        if (queryConditions.size() > 1) {
            sqlCondition = "WHERE " + String.join(" AND ", queryConditions);
        } if (queryConditions.size() == 1) {
            sqlCondition = "WHERE " + queryConditions.get(0);
        }

        List<String> paginationParams = new ArrayList<>();
        if (filter.getOrder() != null) {
            paginationParams.add(" ORDER BY :order");
            params.addValue("order", "player." + filter.getOrder().getFieldName());
        }
        if (filter.getPageNumber() != null && filter.getPageSize() != null) {
            paginationParams.add( "LIMIT :limit");
            params.addValue("limit", filter.getPageSize());
            paginationParams.add("OFFSET :offset");
            params.addValue("offset", filter.getPageSize() * filter.getPageNumber());
        }
        String sqlOption = "";
        if (!paginationParams.isEmpty()) {
            sqlOption = String.join(" ", paginationParams);
        }
        String sql = "SELECT player.id, player.name, player.title, race.name race_name, profession.name profession_name, " +
                "player.level, player.experience, player.until_next_level, player.birthday, player.banned " +
                "FROM player " +
                "JOIN race " +
                "ON player.race_id = race.id " +
                "JOIN profession " +
                "ON player.profession_id = profession.id " + sqlCondition + sqlOption;
        return namedParameterJdbcTemplate.query(sql, params, new PlayerRowMapper());
    }
}
