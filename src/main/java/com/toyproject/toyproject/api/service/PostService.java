package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.repository.PostRepository;
import com.toyproject.toyproject.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        postRepository.save(postCreate.toEntity());
    }

    public Post get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));

        return post;
    }
}
