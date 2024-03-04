package com.example.demo.dao.repository;

import com.example.demo.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Race findByName(String name);
}
