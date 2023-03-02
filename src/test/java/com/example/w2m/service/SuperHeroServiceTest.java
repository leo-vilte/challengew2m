package com.example.w2m.service;

import com.example.w2m.entity.Hero;
import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.mapper.HeroMapper;
import com.example.w2m.model.SuperHero;
import com.example.w2m.repository.SuperHeroRepository;
import com.example.w2m.service.impl.SuperHeroService;
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

    private HeroMapper mapper = Mappers.getMapper(HeroMapper.class);

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

        Optional<SuperHero> superHero = service.findById(8L);
        assertTrue(superHero.isPresent());
        assertEquals("Superman", superHero.get().getName());
    }

    @Test
    public void findById_NotFound() {
        Mockito.when(repository.findById(200L)).thenReturn(Optional.empty());

        Optional<SuperHero> superHero = service.findById(200L);
        assertFalse(superHero.isPresent());
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
    public void create_OK() {
        SuperHero hero = new SuperHero("Robin");

        Mockito.when(repository.save(Mockito.any())).thenReturn(mapper.toEntity(hero));

        assertDoesNotThrow(() -> service.create(hero));
    }

    @Test
    public void create_ERROR_duplicated_name() {
        SuperHero hero = new SuperHero("Batman");

        Mockito.when(repository.save(Mockito.any())).thenThrow(new DataIntegrityViolationException("Exception"));

        assertThrows(HeroExistedException.class,() -> service.create(hero));
    }

    @Test
    public void update_OK() {
        var newName = "Spiderman2";

        SuperHero hero = new SuperHero(newName);
        hero.setId(2);

        Mockito.when(repository.save(Mockito.any())).thenReturn(mapper.toEntity(hero));
        Mockito.when(repository.existsById(2L)).thenReturn(Boolean.TRUE);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(mapper.toEntity(hero)));

        service.update(hero);

        var updatedHero = service.findById(2L);
        assertEquals(newName, updatedHero.get().getName());
    }

    @Test
    public void update_ERROR_ID_notFound() {
        var newName = "Spiderman2";
        SuperHero hero = new SuperHero(newName);
        hero.setId(20);

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
