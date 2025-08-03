package com.example.eventplanner.entity;

import com.example.eventplanner.dto.EventRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Comment> comments;
    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 200)
    private String description;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    public Event(String title, String description, String name, String password) {
        this.title = title;
        this.description = description;
        this.name = name;
        this.password = password;
    }

    public void updateEventFromDto(EventRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
    }

}
