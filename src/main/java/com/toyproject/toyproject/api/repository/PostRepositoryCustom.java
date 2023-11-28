package com.toyproject.toyproject.api.repository;

import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
