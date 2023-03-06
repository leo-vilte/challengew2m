package com.challenge.w2m.controller;


import com.challenge.w2m.dto.LoginDTO;
import com.challenge.w2m.model.SuperHero;
import com.challenge.w2m.service.ISuperHeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:empty_table.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:insert_superheros.sql", executionPhase = BEFORE_TEST_METHOD)
})
class SuperHeroControllerTest {

    @Autowired
    private ISuperHeroService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_ok() throws Exception {
        final var loginDTO = new LoginDTO();
        loginDTO.setUsername("leo_vilte");
        loginDTO.setPassword("prueba01");
        final String loginJson = new ObjectMapper().writeValueAsString(loginDTO);
        this.mockMvc.perform(post("/authenticate/login")
                .contentType(APPLICATION_JSON)
                .content(loginJson)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_create_one_superhero() throws Exception {
        final var superHero = new SuperHero();
        superHero.setName("Robin");

        final String heroToCreate = new ObjectMapper().writeValueAsString(superHero);

        this.mockMvc.perform(post("/superheros")
                        .header(HttpHeaders.AUTHORIZATION, getToken())
                        .contentType(APPLICATION_JSON)
                        .content(heroToCreate))

                .andDo(print())
                .andExpect(status().isCreated());

        assertThat(this.service.getAll()).hasSize(11);
    }
    @Test
    void when_create_one_superhero_with_exists_name_throw_error() throws Exception {
        final var superHero = new SuperHero();
        superHero.setName("Batman");

        final String heroToCreate = new ObjectMapper().writeValueAsString(superHero);

        this.mockMvc.perform(post("/superheros")
                        .header(HttpHeaders.AUTHORIZATION, getToken())
                        .contentType(APPLICATION_JSON)
                        .content(heroToCreate))

                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_retrieve_one_superhero() throws Exception {
        this.mockMvc.perform(get("/superheros/{id}", 6).header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("AntMan"));
    }



    @Test
    void should_retrieve_superhero_by_name() throws Exception {
        this.mockMvc.perform(get("/superheros?name={name}", "man")
                        .header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath(("$[*].name"), Matchers.everyItem(Matchers.containsStringIgnoringCase("man"))));
    }



    @Test
    void should_response_not_found_if_id_not_exits() throws Exception {
        this.mockMvc.perform(get("/superheros/{id}", 40).header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_retrieve_all_superheros() throws Exception {
        this.mockMvc.perform(get("/superheros").header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_delete_one_user() throws Exception {
        this.mockMvc.perform(delete("/superheros/{id}", 2)
                        .header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(this.service.getAll()).hasSize(9);
    }

    private String getToken() throws Exception {
        final var loginDTO = new LoginDTO();
        loginDTO.setUsername("leo_vilte");
        loginDTO.setPassword("prueba01");
        final String loginJson = new ObjectMapper().writeValueAsString(loginDTO);
        return "Bearer ".concat(this.mockMvc.perform(post("/authenticate/login")
                .contentType(APPLICATION_JSON)
                .content(loginJson))
                .andReturn().getResponse().getContentAsString());
    }
}
