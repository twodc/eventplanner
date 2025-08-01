package com.example.eventplanner.dto;

import com.example.eventplanner.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventResponseDto {
    private Long eventId;
    private String title;
    private String description;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public EventResponseDto(Event event) {
        this.eventId = event.getEventId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.name = event.getName();
        this.createdAt = event.getCreatedAt();
        this.modifiedAt = event.getModifiedAt();
    }

}