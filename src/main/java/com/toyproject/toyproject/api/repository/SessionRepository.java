package com.toyproject.toyproject.api.repository;

import com.toyproject.toyproject.api.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByAccessToken(String accessToken);
}
