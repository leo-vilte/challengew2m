package com.example.w2m.model;

public class SuperHero {

    private Long ID;
    private String name;

    public SuperHero() {}

    public SuperHero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(long i) {
        this.ID = i;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
