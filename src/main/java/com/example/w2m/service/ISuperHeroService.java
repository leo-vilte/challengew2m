package com.example.w2m.service;

import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.model.SuperHero;

import java.util.List;
import java.util.Optional;

public interface ISuperHeroService {
    List<SuperHero> getAll();

    Optional<SuperHero> findById(Long i);

    List<SuperHero> findByName(String man);

    Long create(SuperHero hero) throws HeroExistedException;

    SuperHero update(SuperHero hero) throws HeroNotFoundException;

    void deleteById(long i) throws HeroNotFoundException;
}
