package com.example.eventplanner.dto;

import com.example.eventplanner.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentsResponseDto {
    private Long commentsId;
    private String description;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentsResponseDto(Comments comments) {
        this.commentsId = comments.getCommentsId();
        this.description = comments.getDescription();
        this.name = comments.getName();
        this.createdAt = comments.getCreatedAt();
        this.modifiedAt = comments.getModifiedAt();
    }

}
