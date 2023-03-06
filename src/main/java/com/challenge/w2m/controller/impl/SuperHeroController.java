package com.challenge.w2m.controller.impl;

import com.challenge.w2m.annotation.LoggingTime;
import com.challenge.w2m.controller.ISuperheroController;
import com.challenge.w2m.dto.SuperheroDTO;
import com.challenge.w2m.mapper.HeroMapper;
import com.challenge.w2m.model.SuperHero;
import com.challenge.w2m.service.ISuperHeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SuperHeroController implements ISuperheroController {

    private final ISuperHeroService service;
    private final HeroMapper heroMapper;

    public SuperHeroController(ISuperHeroService service, HeroMapper mapper) {
        this.service = service;
        this.heroMapper = mapper;
    }

    @LoggingTime
    @Override
    public ResponseEntity<List<SuperHero>> get(@RequestParam(name = "name", required = false) String name) {
        if(name != null) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<SuperHero> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @LoggingTime
    public ResponseEntity<String> create(@RequestBody(required = false) SuperheroDTO superHero) {
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .toUriString()
                            .concat(String.valueOf(service.create(heroMapper.dtoToModel(superHero)))))
                )
                .build();
    }

    @Override
    public ResponseEntity<SuperHero> update(@RequestBody SuperheroDTO superHero, @PathVariable(name = "id") Long id) {
        var hero = heroMapper.dtoToModel(superHero);
        hero.setId(id);
        return ResponseEntity.ok(service.update(hero));
    }

    @Override
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
