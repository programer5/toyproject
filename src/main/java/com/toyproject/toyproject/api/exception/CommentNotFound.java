package com.toyproject.toyproject.api.exception;

import lombok.Getter;

@Getter
public class CommentNotFound extends HodologException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
