package com.toyproject.toyproject.api.repository.post;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
