package com.toyproject.toyproject.api.config;

import com.toyproject.toyproject.api.config.data.UserSession;
import com.toyproject.toyproject.api.domain.Session;
import com.toyproject.toyproject.api.exception.Unauthorized;
import com.toyproject.toyproject.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = webRequest.getHeader("Authorization");
        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        Session byAccessToken = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);

        return new UserSession(byAccessToken.getMember().getId());

    }
}
