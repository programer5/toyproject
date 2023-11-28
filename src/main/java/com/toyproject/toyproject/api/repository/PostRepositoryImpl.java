package com.toyproject.toyproject.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.toyproject.api.domain.Post;
import com.toyproject.toyproject.api.domain.QPost;
import com.toyproject.toyproject.api.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory japQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return japQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
