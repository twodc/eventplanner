package com.example.eventplanner.controller;

import com.example.eventplanner.dto.CommentRequestDto;
import com.example.eventplanner.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComments(@RequestBody CommentRequestDto requestDto, @PathVariable Long eventId) {
        try {
            return new ResponseEntity<>(commentService.saveComment(requestDto, eventId), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 입력 항목이 누락되었습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Long eventId) {
        try {
            return new ResponseEntity<>(commentService.findAllComments(eventId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(
            @PathVariable Long eventId, @PathVariable Long commentId) {
        try {
            return new ResponseEntity<>(commentService.findCommentById(eventId, commentId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequestDto requestDto,
                                            @PathVariable Long eventId, @PathVariable Long commentId) {
        try {
            commentService.modifyComment(requestDto, eventId, commentId);
            return new ResponseEntity<>(commentService.findCommentById(eventId, commentId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestBody CommentRequestDto requestDto,
                              @PathVariable Long eventId, @PathVariable Long commentId) {
        try {
            commentService.removeComment(requestDto.getPassword(), eventId, commentId);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
