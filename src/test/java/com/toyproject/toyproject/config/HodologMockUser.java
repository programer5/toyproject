package com.toyproject.toyproject.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = HodologMockSecurityContext.class)
public @interface HodologMockUser {

    String email() default "neverdie4757@gmail.com";

    String name() default "호돌맨";

    String password() default "";

//    String role() default "ROLE_ADMIN";
}
