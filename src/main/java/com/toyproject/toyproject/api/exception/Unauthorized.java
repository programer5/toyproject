package com.toyproject.toyproject.api.exception;

import lombok.Getter;

@Getter
public class Unauthorized extends HodologException{

    private static final String MESSAGE = "인증이 필요합니다..";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
