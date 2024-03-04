package com.example.demo.dao.repository;

import com.example.demo.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    Profession findByName(String name);
}
