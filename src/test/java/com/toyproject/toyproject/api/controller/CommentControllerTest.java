package com.toyproject.toyproject.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.toyproject.api.domain.Comment;
import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.repository.UserRepository;
import com.toyproject.toyproject.api.repository.comment.CommentRepository;
import com.toyproject.toyproject.api.repository.post.PostRepository;
import com.toyproject.toyproject.api.request.comment.CommentCreate;
import com.toyproject.toyproject.api.request.comment.CommentDelete;
import com.toyproject.toyproject.config.HodologMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @HodologMockUser
    @DisplayName("댓글 작성")
    void test() throws Exception {

        Member member = Member.builder()
                .name("호돌맨")
                .email("neverdie4757@gmail.com")
                .password("1234")
                .build();

        userRepository.save(member);

        Post post = Post.builder()
                .title("123456")
                .content("bar")
                .member(member)
                .build();

        postRepository.save(post);

        CommentCreate request = CommentCreate.builder()
                .author("호순이")
                .password("123456")
                .content("댓글입니다1234.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(1L, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);
        Assertions.assertEquals("호순이", savedComment.getAuthor());
        Assertions.assertNotEquals("123456", savedComment.getPassword());
        Assertions.assertTrue(passwordEncoder.matches("123456", savedComment.getPassword()));
        Assertions.assertEquals("댓글입니다1234.", savedComment.getContent());
    }

    @Test
    @HodologMockUser
    @DisplayName("댓글 삭제")
    void test2() throws Exception {

        Member member = Member.builder()
                .name("호돌맨")
                .email("neverdie4757@gmail.com")
                .password("1234")
                .build();

        userRepository.save(member);

        Post post = Post.builder()
                .title("123456")
                .content("bar")
                .member(member)
                .build();

        postRepository.save(post);

        Comment comment = Comment.builder()
                .author("호순이")
                .password(passwordEncoder.encode("123456"))
                .content("댓글입니다1234.")
                .build();

        comment.setPost(post);
        commentRepository.save(comment);

        CommentDelete request = new CommentDelete("123456");

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments/{commentId}/delete", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}