package com.example.w2m.service;

import com.example.w2m.dto.LoginDTO;

public interface IAuthenticateService {
    String authenticate(LoginDTO loginDto);
}
