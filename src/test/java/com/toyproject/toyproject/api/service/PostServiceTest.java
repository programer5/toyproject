package com.toyproject.toyproject.api.service;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.exception.PostNotFound;
import com.toyproject.toyproject.api.repository.PostRepository;
import com.toyproject.toyproject.api.request.PostCreate;
import com.toyproject.toyproject.api.request.PostEdit;
import com.toyproject.toyproject.api.request.PostSearch;
import com.toyproject.toyproject.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("호돌맨 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();


        // when
        List<PostResponse> posts = postService.getList(postSearch);
        // then
        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("호돌맨 제목 19", posts.get(0).getTitle());
        Assertions.assertEquals("호돌맨 제목 15", posts.get(4).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        Assertions.assertEquals("호돌걸", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌맨")
                .content("초가집")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        Assertions.assertEquals("초가집", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test6() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        Assertions.assertEquals(null, changedPost.getTitle());
        Assertions.assertEquals("초가집", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        postService.delete(post.getId());

        Assertions.assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test8() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // when
        PostNotFound e = Assertions.assertThrows(PostNotFound.class, () -> postService.get(post.getId() + 1L));

        // then
        Assertions.assertEquals("존재하지 않는 글입니다.", e.getMessage());

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        Assertions.assertThrows(PostNotFound.class, () -> postService.get(post.getId() + 1L));
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글입니다.")
    void test10() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        Assertions.assertThrows(PostNotFound.class, () -> postService.get(post.getId() + 1L));

    }
}