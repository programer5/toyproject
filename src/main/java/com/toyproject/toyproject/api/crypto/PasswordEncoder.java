package com.toyproject.toyproject.api.crypto;

public interface PasswordEncoder {

    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encyptPassword);
}