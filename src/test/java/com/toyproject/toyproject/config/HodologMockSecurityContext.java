package com.toyproject.toyproject.config;

import com.toyproject.toyproject.api.config.UserPrincipal;
import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class HodologMockSecurityContext implements WithSecurityContextFactory<HodologMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(HodologMockUser annotation) {
        Member member = Member.builder()
                .email(annotation.email())
                .name(annotation.name())
                .password(annotation.password())
                .build();

        userRepository.save(member);

        UserPrincipal userPrincipal = new UserPrincipal(member);

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_ADMIN");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, member.getPassword(), List.of(role));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        return context;
    }
}
