package com.example.demo.dao.repository;

import com.example.demo.entity.RaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<RaceEntity, Long> {
    RaceEntity findByName(String name);
}
