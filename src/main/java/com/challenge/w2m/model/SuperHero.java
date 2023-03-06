package com.challenge.w2m.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class SuperHero {

    @Schema( type = "number", example = "2")
    private Long id;


    @Schema( type = "String", example = "Superman")
    private String name;

    public SuperHero() {}

    public SuperHero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
