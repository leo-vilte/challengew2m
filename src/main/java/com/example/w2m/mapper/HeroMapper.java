package com.example.w2m.mapper;

import com.example.w2m.dto.SuperheroDTO;
import com.example.w2m.entity.Hero;
import com.example.w2m.model.SuperHero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    SuperHero toModel(Hero hero);

    Hero toEntity(SuperHero hero);

    SuperHero dtoToModel(SuperheroDTO dto);

}
