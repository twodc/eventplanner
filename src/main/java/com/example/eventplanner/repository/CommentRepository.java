package com.example.eventplanner.repository;

import com.example.eventplanner.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent_EventId(Long eventId);
    Optional<Comment> findByEvent_EventIdAndCommentId(Long eventId, Long commentId);
    long countByEvent_EventId(Long eventId);
}
