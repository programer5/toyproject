package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.crypto.PasswordEncoder;
import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.exception.AlreadyExistsEmailException;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.request.Signup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(Signup signup) {

        Optional<Member> byEmail = userRepository.findByEmail(signup.getEmail());

        if (byEmail.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        Member member = Member.builder()
                .name(signup.getName())
                .password(passwordEncoder.encrypt(signup.getPassword()))
                .email(signup.getEmail())
                .build();

        userRepository.save(member);
    }
}
