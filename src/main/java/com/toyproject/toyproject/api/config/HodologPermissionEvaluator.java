package com.toyproject.toyproject.api.config;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.exception.PostNotFound;
import com.toyproject.toyproject.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class HodologPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Post post = postRepository.findById((Long) targetId)
                .orElseThrow(PostNotFound::new);

        if (!post.getMemberId().equals(userPrincipal.getUserId())) {
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다. targetId={}", targetId);
            return false;
        }

        return true;
    }
}
