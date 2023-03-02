package com.example.w2m.service.impl;

import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.mapper.HeroMapper;
import com.example.w2m.model.SuperHero;
import com.example.w2m.repository.SuperHeroRepository;
import com.example.w2m.service.ISuperHeroService;
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
    public List<SuperHero> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<SuperHero> findById(Long i) {
        return repository
                .findById(i)
                .map(mapper::toModel);
    }

    @Override
    public List<SuperHero> findByName(String name) {
        return repository
                .getHeroesByNameContainsIgnoreCase(name)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Long create(SuperHero hero) throws HeroExistedException {
        try {
            return repository
                    .save(mapper.toEntity(hero))
                    .getId();
        } catch (DataIntegrityViolationException e) {
            throw new HeroExistedException();
        }
    }

    @Override
    public SuperHero update(SuperHero hero) throws HeroNotFoundException {
        if (repository.existsById(hero.getID())) {
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
    public void deleteById(long id) throws HeroNotFoundException {
        repository.deleteById(id);

    }
}
