package com.challenge.w2m.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import java.io.Serializable;


@Entity
public class Role implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Enumerated(EnumType.STRING)
    private RoleName roleName ;

    public Role (RoleName roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }

    public String getRoleName() {
        return roleName.toString();
    }
}