package com.example.eventplanner.service;

import com.example.eventplanner.dto.CommentsRequestDto;
import com.example.eventplanner.dto.CommentsResponseDto;
import com.example.eventplanner.entity.Comments;
import com.example.eventplanner.entity.Event;
import com.example.eventplanner.repository.CommentsRepository;
import com.example.eventplanner.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final EventRepository eventRepository;
    private final CommentsRepository commentsRepository;

    // 댓글 생성
    @Transactional
    public CommentsResponseDto saveComments(CommentsRequestDto requestDto, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));
        Comments comments = new Comments(
                event, requestDto.getDescription(), requestDto.getName(), requestDto.getPassword());
        Comments savedComments = commentsRepository.save(comments);

        return new CommentsResponseDto(
                savedComments.getCommentsId(), savedComments.getDescription(), savedComments.getName(),
                savedComments.getCreatedAt(), savedComments.getModifiedAt());
    }

    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentsResponseDto> findAllComments(Long eventId) { // select * from comments where eventId
        List<Comments> comments = commentsRepository.findAll();
        return comments.stream().map(CommentsResponseDto::new).toList();
    }

    // 선택 댓글 조회
    @Transactional(readOnly = true)
    public CommentsResponseDto findCommentById(Long commentsId) {
        Comments comment = commentsRepository.findById(commentsId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        return new CommentsResponseDto(
                comment.getCommentsId(), comment.getDescription(), comment.getName(),
                comment.getCreatedAt(), comment.getModifiedAt());
    }

    // 댓글 수정
    @Transactional
    public void modifyComment(CommentsRequestDto requestDto, Long commentsId) {
        Comments comment = commentsRepository.findById(commentsId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        if (comment.getPassword().equals(requestDto.getPassword())) {
            comment.updateCommentFromDto(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 댓글 삭제
    @Transactional
    public void removeComment(String password, Long id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        if (comment.getPassword().equals(password)) {
            commentsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
