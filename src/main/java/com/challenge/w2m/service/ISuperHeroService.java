package com.challenge.w2m.service;

import com.challenge.w2m.model.SuperHero;

import java.util.List;

public interface ISuperHeroService {
    List<SuperHero> getAll();

    SuperHero findById(Long i);

    List<SuperHero> findByName(String man);

    Long create(SuperHero hero);

    SuperHero update(SuperHero hero);

    void deleteById(long i);
}
