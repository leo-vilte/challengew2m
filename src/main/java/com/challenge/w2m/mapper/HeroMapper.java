package com.challenge.w2m.mapper;

import com.challenge.w2m.dto.SuperheroDTO;
import com.challenge.w2m.entity.Hero;
import com.challenge.w2m.model.SuperHero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    SuperHero toModel(Hero hero);

    Hero toEntity(SuperHero hero);

    SuperHero dtoToModel(SuperheroDTO dto);

}
