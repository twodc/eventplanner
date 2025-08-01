package com.example.eventplanner.entity;

import com.example.eventplanner.dto.CommentsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comments extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentsId;
    @ManyToOne
    private Event event;
    @Column(nullable = false, length = 100)
    private String description;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    public Comments(Event event, String description, String name, String password) {
        this.event = event;
        this.description = description;
        this.name = name;
        this.password = password;
    }

    public void updateCommentFromDto(CommentsRequestDto requestDto) {
        this.description = requestDto.getDescription();
    }

}
