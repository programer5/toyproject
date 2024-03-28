package com.toyproject.toyproject.api.repository.comment;

import com.toyproject.toyproject.api.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
