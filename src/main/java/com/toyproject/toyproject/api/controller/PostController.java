package com.toyproject.toyproject.api.controller;

import com.toyproject.toyproject.api.request.PostCreate;
import com.toyproject.toyproject.api.request.PostEdit;
import com.toyproject.toyproject.api.request.PostSearch;
import com.toyproject.toyproject.api.response.PostResponse;
import com.toyproject.toyproject.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request, @RequestHeader String authorization) throws Exception {
        if (authorization.equals("hodolman")) {
            request.validate();
            postService.write(request);
        }
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit, @RequestHeader String authorization) {
        if (authorization.equals("hodolman")) {
            postService.edit(postId, postEdit);
        }
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId, @RequestHeader String authorization) {
        if (authorization.equals("hodolman")) {
            postService.delete(postId);
        }
    }
}
