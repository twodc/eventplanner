package com.example.eventplanner.service;

import com.example.eventplanner.dto.CommentRequestDto;
import com.example.eventplanner.dto.CommentResponseDto;
import com.example.eventplanner.entity.Comment;
import com.example.eventplanner.entity.Event;
import com.example.eventplanner.repository.CommentRepository;
import com.example.eventplanner.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto requestDto, Long eventId) {
        if (commentRepository.countByEvent_EventId(eventId) >= 10) {
            throw new RuntimeException("해당 일정에 더 이상 댓글을 작성할 수 없습니다.");
        } else {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));
            Comment comment = new Comment(
                    event, requestDto.getDescription(), requestDto.getName(), requestDto.getPassword());
            Comment savedComment = commentRepository.save(comment);

            return new CommentResponseDto(savedComment);
        }
    }

    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments(Long eventId) {
        List<Comment> comments = commentRepository.findByEvent_EventId(eventId);

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    // 선택 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentById(Long eventId, Long commentId) {
        Comment comment = commentRepository.findByEvent_EventIdAndCommentId(eventId, commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public void modifyComment(CommentRequestDto requestDto, Long eventId, Long commentId) {
        Comment comment = commentRepository.findByEvent_EventIdAndCommentId(eventId, commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (comment.getPassword().equals(requestDto.getPassword())) {
            comment.updateCommentFromDto(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 댓글 삭제
    @Transactional
    public void removeComment(String password, Long eventId, Long commentId) {
        Comment comment = commentRepository.findByEvent_EventIdAndCommentId(eventId, commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (comment.getPassword().equals(password)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
