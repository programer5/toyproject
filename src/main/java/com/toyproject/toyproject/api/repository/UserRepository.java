package com.toyproject.toyproject.api.repository;

import com.toyproject.toyproject.api.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
}
