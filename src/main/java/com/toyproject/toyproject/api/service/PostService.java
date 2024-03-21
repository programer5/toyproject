package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.exception.PostNotFound;
import com.toyproject.toyproject.api.exception.UserNotFound;
import com.toyproject.toyproject.api.repository.PostRepository;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.request.PostCreate;
import com.toyproject.toyproject.api.request.PostEdit;
import com.toyproject.toyproject.api.request.PostSearch;
import com.toyproject.toyproject.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void write(Long userId, PostCreate postCreate) {
        Member member = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        postRepository.save(postCreate.toEntity(member));
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        post.edit(postEdit.getTitle(), postEdit.getContent());

    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
