package com.challenge.w2m.repository;

import com.challenge.w2m.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> getHeroesByNameContainsIgnoreCase(String name);
}
