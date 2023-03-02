package com.example.w2m.controller;

import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.model.SuperHero;
import com.example.w2m.service.ISuperHeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/superheros")
public class SuperHeroController {

    private ISuperHeroService service;

    public SuperHeroController(ISuperHeroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SuperHero>> get(@RequestParam(name = "name", required = false) String name) {
        if(name != null) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHero> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody(required = false) SuperHero superHero) throws HeroExistedException {
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .toUriString()
                            .concat(String.valueOf(service.create(superHero))))
                )
                .build();
    }

    @PutMapping
    public ResponseEntity<SuperHero> update(@RequestBody SuperHero superHero) throws HeroNotFoundException {
        return ResponseEntity.ok(service.update(superHero));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws HeroNotFoundException {
        service.deleteById(id);
    }





}
