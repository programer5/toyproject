package com.toyproject.toyproject.api.controller;

import com.toyproject.toyproject.api.config.AppConfig;
import com.toyproject.toyproject.api.request.Login;
import com.toyproject.toyproject.api.response.SessionResponse;
import com.toyproject.toyproject.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        Long userId = authService.signin(login);

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(appConfig.getJwtKey())
                .issuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }
}
