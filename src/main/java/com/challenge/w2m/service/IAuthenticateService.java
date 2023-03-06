package com.challenge.w2m.service;

import com.challenge.w2m.dto.LoginDTO;

public interface IAuthenticateService {
    String authenticate(LoginDTO loginDto);
}
