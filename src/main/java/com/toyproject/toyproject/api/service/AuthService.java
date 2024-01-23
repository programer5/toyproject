package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.exception.InvalidSigninInformation;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {

        Member user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return user.getId();
    }

}
