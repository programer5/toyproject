package com.toyproject.toyproject.api.config;

import com.toyproject.toyproject.api.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN")
                ));
        this.userId = member.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
