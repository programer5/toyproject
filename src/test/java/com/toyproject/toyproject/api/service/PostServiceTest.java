package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.repository.PostRepository;
import com.toyproject.toyproject.api.request.PostCreate;
import com.toyproject.toyproject.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);
        // when
        PostResponse postResponse = postService.get(post.getId());
        // then
        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals("제목입니다.", postResponse.getTitle());
        Assertions.assertEquals("내용입니다.", postResponse.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        Post post1 = Post.builder()
                .title("제목입니다1.")
                .content("내용입니다1.")
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("제목입니다2.")
                .content("내용입니다2.")
                .build();
        postRepository.save(post2);
        // when
        List<PostResponse> posts = postService.getList();
        // then
        Assertions.assertEquals(2L, posts.size());

    }

}