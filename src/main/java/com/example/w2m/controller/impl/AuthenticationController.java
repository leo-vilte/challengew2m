package com.example.w2m.controller.impl;

import com.example.w2m.controller.IAuthenticationController;
import com.example.w2m.dto.LoginDTO;
import com.example.w2m.service.impl.AuthenticateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate/")
public class AuthenticationController implements IAuthenticationController {

    private AuthenticateService authenticateService;

    public AuthenticationController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    @PostMapping("login")
    public String authenticate(@RequestBody LoginDTO loginDto) {
        return  authenticateService.authenticate(loginDto);
    }
}
