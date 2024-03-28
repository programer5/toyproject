package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Comment;
import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.exception.PostNotFound;
import com.toyproject.toyproject.api.repository.comment.CommentRepository;
import com.toyproject.toyproject.api.repository.post.PostRepository;
import com.toyproject.toyproject.api.request.comment.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(passwordEncoder.encode(request.getPassword()))
                .content(request.getContent())
                .build();

        post.addComment(comment);
    }
}
