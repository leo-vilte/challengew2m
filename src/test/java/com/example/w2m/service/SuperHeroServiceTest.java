package com.example.w2m.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class SuperHeroServiceTest {

    @Mock
    public SuperHeroService service;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void retrieveAll_OK() {
        List<SuperHero> superHeroes = service.getAll();
        assertEquals(10, superHeroes.size());
    }

    @Test
    public void findById_OK() {
        Optional<SuperHero> superHero = service.findById(8);
        assertTrue(superHero.isPresent());
        assertEquals("Superman", superHero.getName());
    }

    @Test
    public void findById_NotFound() {
        Optional<SuperHero> superHero = service.findById(200);
        assertFalse(superHero.isPresent());
    }

    @Test
    public void findByName_OK() {
        List<SuperHero> heroes = service.findByName("man");
        assertFalse(heroes.isEmpty());
    }

    @Test
    public void findByName_NotFound() {
        List<SuperHero> heroes = service.findByName("pepe");
        assertTrue(heroes.isEmpty());
    }

    @Test
    public void create_OK() {
        SuperHero hero = new SuperHero("Robin");

        assertDoesNotThrow(() -> service.create(hero));
    }

    @Test
    public void create_ERROR_duplicated_name() {
        SuperHero hero = new SuperHero("Batman");
        assertThrows(HeroExistedException.class,() -> service.create(hero));
    }

    @Test
    public void update_OK() {
        var newName = "Spiderman2";

        SuperHero hero = new SuperHero(newName);
        hero.setId(2);
        service.update(hero);

        var updatedHero = service.findById(2);
        assertEquals(newName, updatedHero.getName());
    }

    @Test
    public void update_ERROR_ID_notFound() {
        var newName = "Spiderman2";
        SuperHero hero = new SuperHero(newName);
        hero.setId(20);

        assertThrows(HeroNotFoundException.class,() -> service.update(hero));
    }

    @Test
    public void delete_OK() {
        Optional<SuperHero> hero = service.findById(5);
        assertTrue(hero.isPresent());

        service.deleteById(5);

        hero = service.findById(5);
        assertTrue(hero.isEmpty());
    }

    @Test
    public void delete_ID_not_found() {
        assertThrows(HeroNotFoundException.class, ()-> service.deleteById(20));
    }


}
