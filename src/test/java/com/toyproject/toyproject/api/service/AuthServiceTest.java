package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.crypto.PasswordEncoder;
import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.exception.AlreadyExistsEmailException;
import com.toyproject.toyproject.api.exception.InvalidSigninInformation;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.request.Login;
import com.toyproject.toyproject.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
        Assertions.assertNotEquals("1234", member.getPassword());
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

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        PasswordEncoder encoder = new PasswordEncoder();
        String encrypt = encoder.encrypt("1234");
        Member member1 = Member.builder()
                .email("neverdie4757@gmail.com")
                .password(encrypt)
                .name("짱돌맨")
                .build();

        Member member = userRepository.save(member1);

        Login login = Login.builder()
                .email("neverdie4757@gmail.com")
                .password("1234")
                .build();

        Long memberId = authService.signin(login);

        Assertions.assertNotNull(memberId);

    }

    @Test
    @DisplayName("로그인 시 비밀번호 틀림")
    void test4() {
        PasswordEncoder encoder = new PasswordEncoder();
        String encrypt = encoder.encrypt("1234");
        Member member1 = Member.builder()
                .email("neverdie4757@gmail.com")
                .password(encrypt)
                .name("짱돌맨")
                .build();

        Member member = userRepository.save(member1);

        Login login = Login.builder()
                .email("neverdie4757@gmail.com")
                .password("5678")
                .build();

        Assertions.assertThrows(InvalidSigninInformation.class, () -> authService.signin(login));


    }

}