package com.toyproject.toyproject.api.controller;

import com.toyproject.toyproject.api.request.Login;
import com.toyproject.toyproject.api.response.SessionResponse;
import com.toyproject.toyproject.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String KEY = "fgIBqD11TOyi9YSHqZ8Pzt7EndLGo5W10PC6GgiB5vs=";

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        Long userId = authService.signin(login);

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }
}
