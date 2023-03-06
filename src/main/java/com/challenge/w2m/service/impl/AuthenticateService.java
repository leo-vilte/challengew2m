package com.challenge.w2m.service.impl;

import com.challenge.w2m.dto.LoginDTO;
import com.challenge.w2m.entity.Role;
import com.challenge.w2m.repository.IUserRepository;
import com.challenge.w2m.security.JwtComponent;
import com.challenge.w2m.service.IAuthenticateService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticateService implements IAuthenticateService {

    private final JwtComponent jwtComponent;
    private final AuthenticationManager authenticationManager;

    private final IUserRepository repository;

    public AuthenticateService(JwtComponent jwtComponent, AuthenticationManager authenticationManager, IUserRepository repository) {
        this.jwtComponent = jwtComponent;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @Override
    public String authenticate(LoginDTO loginDto) {
        var authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = repository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var rolesNames = new ArrayList<>(user.getRoles().stream().map(Role::getRoleName).toList());
        return jwtComponent.generateToken(user.getUsername(),rolesNames);
    }
}
