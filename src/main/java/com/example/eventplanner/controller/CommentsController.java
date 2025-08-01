package com.example.eventplanner.controller;

import com.example.eventplanner.dto.CommentsRequestDto;
import com.example.eventplanner.dto.CommentsResponseDto;
import com.example.eventplanner.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public CommentsResponseDto createComments(@RequestBody CommentsRequestDto requestDto, @PathVariable Long eventId) {
        return commentsService.saveComments(requestDto, eventId);
    }

    @GetMapping
    public List<CommentsResponseDto> getComments(@PathVariable Long eventId) {
        return commentsService.findAllComments(eventId);
    }

    @GetMapping("/{commentsId}")
    public CommentsResponseDto getComment(@PathVariable Long commentsId) {
        return commentsService.findCommentById(commentsId);
    }

    @PutMapping("/{commentsId}")
    public CommentsResponseDto updateComment(@RequestBody CommentsRequestDto requestDto, @PathVariable Long commentsId) {
        commentsService.modifyComment(requestDto, commentsId);
        return commentsService.findCommentById(commentsId);
    }

    @DeleteMapping("/{commentsId}")
    public void deleteComment(@RequestBody CommentsRequestDto requestDto, @PathVariable Long commentsId) {
        commentsService.removeComment(requestDto.getPassword(), commentsId);
    }

}
