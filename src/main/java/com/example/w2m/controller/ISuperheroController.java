package com.example.w2m.controller;

import com.example.w2m.dto.SuperheroDTO;
import com.example.w2m.exception.HeroExistedException;
import com.example.w2m.exception.HeroNotFoundException;
import com.example.w2m.model.SuperHero;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/superheros")
@Tag(name = "Superheros",description = "ABM Superheros")
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public interface ISuperheroController {

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Returns a list of superheros",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                        content =  @Content( mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = SuperHero.class)))
                ),
                @ApiResponse(responseCode = "500", description = "Internal Error",
                        content = @Content(schema = @Schema())
                )
            }
    )
    ResponseEntity<List<SuperHero>> get(@Parameter(example = "man") @RequestParam(name = "name", required = false) String name);

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get a superhero by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                            content =  @Content( mediaType = "application/json",schema = @Schema(implementation = SuperHero.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Hero not Found",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    ResponseEntity<SuperHero> getById(@Parameter(example = "1", required = true) @PathVariable Long id);

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Create a superhero",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Superhero created",
                            content =  @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "Hero already Exits",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    ResponseEntity<String> create(@RequestBody(required = false) SuperheroDTO superHero) throws HeroExistedException;

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update a superhero",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Superhero updated",
                            content =  @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "404", description = "Hero not found",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    ResponseEntity<SuperHero> update(@RequestBody SuperheroDTO superHero, @PathVariable Long id) throws HeroNotFoundException;

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Delete a superhero",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Superhero deleted",
                            content =  @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "404", description = "Hero not found",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    void delete(@PathVariable Long id) throws HeroNotFoundException;
}
