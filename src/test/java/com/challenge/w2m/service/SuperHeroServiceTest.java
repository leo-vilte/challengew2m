package com.challenge.w2m.service;

import com.challenge.w2m.repository.SuperHeroRepository;
import com.challenge.w2m.entity.Hero;
import com.challenge.w2m.exception.HeroExistedException;
import com.challenge.w2m.exception.HeroNotFoundException;
import com.challenge.w2m.mapper.HeroMapper;
import com.challenge.w2m.model.SuperHero;
import com.challenge.w2m.service.impl.SuperHeroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SuperHeroServiceTest {

    private ISuperHeroService service;

    private final HeroMapper mapper = Mappers.getMapper(HeroMapper.class);

    @Mock
    private SuperHeroRepository repository = Mockito.mock(SuperHeroRepository.class);



    @BeforeEach
    public void setup() {
        Mockito.clearInvocations(repository);
        service = new SuperHeroService(repository, mapper);
    }

    @Test
    public void retrieveAll_OK() {
        var heroes = new ArrayList<Hero>();
        var hero = new Hero();
        hero.setName("Spiderman");
        hero.setId(1L);
        heroes.add(hero);

        hero = new Hero();
        hero.setName("Superman");
        hero.setId(2L);
        heroes.add(hero);


        Mockito.when(repository.findAll()).thenReturn(heroes);

        List<SuperHero> superHeroes = service.getAll();
        assertEquals(2, superHeroes.size());
    }

    @Test
    public void findById_OK() {
        var hero = new Hero();
        hero.setName("Superman");
        hero.setId(8L);


        Mockito.when(repository.findById(8L)).thenReturn(Optional.of(hero));

        SuperHero superHero = service.findById(8L);
        assertEquals("Superman", superHero.getName());
    }

    @Test
    public void findById_NotFound() {
        Mockito.when(repository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(HeroNotFoundException.class, () -> service.findById(200L));
    }

    @Test
    public void findByName_OK() {
        var heroes = new ArrayList<Hero>();
        var hero = new Hero();
        hero.setName("Spiderman");
        hero.setId(1L);
        heroes.add(hero);

        hero = new Hero();
        hero.setName("Superman");
        hero.setId(2L);
        heroes.add(hero);

        Mockito.when(repository.getHeroesByNameContainsIgnoreCase("man"))
                .thenReturn(heroes);

        List<SuperHero> heroesResp = service.findByName("man");
        assertFalse(heroesResp.isEmpty());
        for (var superHero: heroesResp) {
            assertTrue(superHero.getName().contains("man"));
        }

    }

    @Test
    public void findByName_NotFound() {
        Mockito.when(repository.getHeroesByNameContainsIgnoreCase("pepe"))
                .thenReturn(List.of());


        List<SuperHero> heroes = service.findByName("pepe");
        assertTrue(heroes.isEmpty());
    }

    @Test
    public void when_created_a_superhero_not_existed_does_not_throw_exception() {
        SuperHero hero = new SuperHero("Robin");

        Mockito.when(repository.save(Mockito.any())).thenReturn(mapper.toEntity(hero));

        Assertions.assertDoesNotThrow(() -> service.create(hero));
    }

    @Test
    public void when_superhero_name_already_exists_throw_error() {
        SuperHero hero = new SuperHero("Batman");

        Mockito.when(repository.save(Mockito.any())).thenThrow(new DataIntegrityViolationException("Exception"));

        assertThrows(HeroExistedException.class,() -> service.create(hero));
    }

    @Test
    public void when_update_superhero_existed_return_ok_and_modify_name() {
        var newName = "Spiderman2";

        SuperHero hero = new SuperHero(newName);
        hero.setId(2L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(mapper.toEntity(hero));
        Mockito.when(repository.existsById(2L)).thenReturn(Boolean.TRUE);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(mapper.toEntity(hero)));

        service.update(hero);

        var updatedHero = service.findById(2L);
        assertEquals(newName, updatedHero.getName());
    }

    @Test
    public void when_try_update_superhero_with_not_exists_id_throw_error() {
        var newName = "Spiderman2";
        SuperHero hero = new SuperHero(newName);
        hero.setId(20L);

        Mockito.when(repository.findById(20L)).thenReturn(Optional.empty());

        HeroNotFoundException heroNotFoundException =
                assertThrows(HeroNotFoundException.class, () -> service.update(hero));
    }

    @Test
    public void delete_OK() {

        Mockito.doNothing().when(repository).deleteById(5L);

        assertDoesNotThrow(() -> service.deleteById(5));

    }


}
