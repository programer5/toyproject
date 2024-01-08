package com.toyproject.toyproject.api.config.data;

import lombok.Data;

@Data
public class UserSession {

    private final Long id;

    public UserSession(Long id) {
        this.id = id;
    }
}
