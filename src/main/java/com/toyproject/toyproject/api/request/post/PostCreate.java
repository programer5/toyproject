package com.toyproject.toyproject.api.request.post;

import com.toyproject.toyproject.api.domain.Member;
import com.toyproject.toyproject.api.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
