package com.toyproject.toyproject.api.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreate {
    private String title;
    private String content;
}
