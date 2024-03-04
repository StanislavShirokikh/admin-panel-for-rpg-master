package com.example.demo.dao.repository.specification;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
@RequiredArgsConstructor
public class PlayerSpecification implements Specification<Player> {
    private final Filter filter;

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (hasText(filter.getName())) {
        predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
        }
        if (hasText(filter.getTitle())) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
        }
        if (hasText(filter.getRace())) {
            Join<Player, Race> raceJoin = root.join("race", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(raceJoin.get("name"), filter.getRace()));
        }
        if (hasText(filter.getProfession())) {
            Join<Player, Profession> raceJoin = root.join("profession", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(raceJoin.get("name"), filter.getProfession()));
        }
        if (filter.getBefore() != null) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), filter.getBefore()));
        }
        if (filter.getAfter() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), filter.getAfter()));
        }
        if (filter.getBanned() != null) {
            predicates.add(criteriaBuilder.equal(root.get("banned"), filter.getBanned()));
        }
        if (filter.getMinExperience() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), filter.getMinExperience()));
        }
        if (filter.getMaxExperience() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), filter.getMaxExperience()));
        }
        if (filter.getMinLevel() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), filter.getMinLevel()));
        }
        if (filter.getMaxLevel() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), filter.getMaxLevel()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
