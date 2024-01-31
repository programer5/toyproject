package com.toyproject.toyproject.api.config;

import com.toyproject.toyproject.api.config.data.UserSession;
import com.toyproject.toyproject.api.exception.Unauthorized;
import com.toyproject.toyproject.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String jws = webRequest.getHeader("Authorization");

        if (jws == null || jws.isEmpty()) {
            throw new Unauthorized();
        }

        try {

            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(appConfig.getJwtKey())
                    .build()
                    .parseSignedClaims(jws);

            String userId = claimsJws.getPayload().getSubject();
            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
