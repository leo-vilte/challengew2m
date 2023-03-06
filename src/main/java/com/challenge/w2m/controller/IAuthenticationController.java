package com.challenge.w2m.controller;

import com.challenge.w2m.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Authentication",description = "Login Request")
public interface IAuthenticationController {
    @PostMapping("login")
    @Operation(summary = "Login with user and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully login return jwt",
                            content =  @Content( mediaType = "text/plain",schema = @Schema(example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZW9fdmlsdGUiLCJyb2xlIjpbIkFETUlOIl0sImlhdCI6MTY3Nzk4MjE1NiwiZXhwIjoxNjc4MDE4MTU2fQ.qjxDahmBpO4X41dGzgPKJAn4o-o8zhX0sU_PdXw4q9EeaYyNsONTpzkSCG2VdKIC"))
                    ),
                    @ApiResponse(responseCode = "403", description = "Invalid Credentials",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    String authenticate(@RequestBody LoginDTO loginDto);
}
