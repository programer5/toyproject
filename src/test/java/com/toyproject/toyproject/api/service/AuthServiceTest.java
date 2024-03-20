package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.exception.AlreadyExistsEmailException;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 성공")
    void test1() {
        Signup signup = Signup.builder()
                .name("정민서")
                .password("1234")
                .email("neverdie4757@gmail.com")
                .build();

        authService.signup(signup);

        Assertions.assertEquals(1, userRepository.count());

        Member member = userRepository.findAll().iterator().next();

        Assertions.assertEquals("neverdie4757@gmail.com", member.getEmail());
        Assertions.assertNotNull(member.getPassword());
        Assertions.assertEquals("1234", member.getPassword());
        Assertions.assertEquals("정민서", member.getName());
    }

    @Test
    @DisplayName("회원 가입시 중복된 이메일")
    void test2() {

        Member member1 = Member.builder()
                .email("neverdie4757@gmail.com")
                .password("1234")
                .name("짱돌맨")
                .build();

        userRepository.save(member1);

        Signup signup = Signup.builder()
                .name("정민서")
                .password("1234")
                .email("neverdie4757@gmail.com")
                .build();

        Assertions.assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

}