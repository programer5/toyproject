package com.toyproject.toyproject.api.controller;

import com.toyproject.toyproject.api.request.Login;
import com.toyproject.toyproject.api.response.SessionResponse;
import com.toyproject.toyproject.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        String accessToken = authService.signin(login);
        return new SessionResponse(accessToken);
    }

}
