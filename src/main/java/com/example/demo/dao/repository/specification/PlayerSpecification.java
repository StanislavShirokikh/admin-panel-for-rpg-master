package com.example.demo.dao.repository.specification;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.ProfessionEntity;
import com.example.demo.entity.Race;
import com.example.demo.entity.RaceEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor
public class PlayerSpecification  {
    public static Specification<Player> nameLike (String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }
    public static Specification<Player> titleLike (String title) {
        if (!hasText(title)) {
            return null;
        }
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + title + "%"));
    }
    public static Specification<Player> equalsRaceName (Race race) {
        if (race == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<Player, RaceEntity> raceJoin = root.join("raceEntity", JoinType.INNER);
            return criteriaBuilder.equal(raceJoin.get("name"), String.valueOf(race));
        });
    }
    public static Specification<Player> equalsProfessionName (Profession profession) {
        if (profession == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<Player, ProfessionEntity> raceJoin = root.join("professionEntity", JoinType.INNER);
            return criteriaBuilder.equal(raceJoin.get("name"), String.valueOf(profession));
        });
    }
    public static Specification<Player> greaterThanOrEqualToBeforeDate (Date before) {
        if (before == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), before));
    }
    public static Specification<Player> lessThanOrEqualToAfterDate (Date after) {
        if (after == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), after));
    }
    public static Specification<Player> equalsBanned (Boolean banned) {
        if (banned == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("banned"), banned));
    }
    public static Specification<Player>  greaterThanOrEqualToMinExperience(Integer minExperience) {
        if (minExperience == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience));
    }
    public static Specification<Player> lessThanOrEqualToMaxExperience(Integer maxExperience) {
        if (maxExperience == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience));

    }
    public static Specification<Player> greaterThanOrEqualToMinLevel(Integer minLevel) {
        if (minLevel == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel));

    }
    public static Specification<Player> lessThanOrEqualToMaxLevel(Integer maxLevel) {
        if (maxLevel == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel));
    }
}
