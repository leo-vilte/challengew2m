package com.example.w2m.service.impl;

import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.mapper.HeroMapper;
import com.example.w2m.model.SuperHero;
import com.example.w2m.repository.SuperHeroRepository;
import com.example.w2m.service.ISuperHeroService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroService implements ISuperHeroService {

    private SuperHeroRepository repository;
    private HeroMapper mapper;


    public SuperHeroService(SuperHeroRepository repository, HeroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable("heroes")
    public List<SuperHero> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public SuperHero findById(Long i) {
        return repository
                .findById(i)
                .map(mapper::toModel)
                .orElseThrow(HeroNotFoundException::new);
    }

    @Override
    @Cacheable("heroes")
    public List<SuperHero> findByName(String name) {
        return repository
                .getHeroesByNameContainsIgnoreCase(name)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    @CacheEvict(value = "heroes", allEntries = true)
    public Long create(SuperHero hero) {
        try {
            return repository
                    .save(mapper.toEntity(hero))
                    .getId();
        } catch (DataIntegrityViolationException e) {
            throw new HeroExistedException();
        }
    }

    @Override
    @CacheEvict(value = "heroes", allEntries = true)
    public SuperHero update(SuperHero hero) {
        if (repository.existsById(hero.getId())) {
            return mapper
                    .toModel(
                        repository
                            .save(mapper.toEntity(hero))
                    );
        } else {
            throw new HeroNotFoundException();
        }
    }

    @Override
    @CacheEvict(value = "heroes", allEntries = true)
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}