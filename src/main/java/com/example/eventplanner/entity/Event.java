package com.example.eventplanner.entity;

import com.example.eventplanner.dto.EventRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // Table과 매핑되는 클래스
@Getter
@NoArgsConstructor
public class Event extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String name;
    private String password;

    public Event(String title, String description, String name, String password) {
        this.title = title;
        this.description = description;
        this.name = name;
        this.password = password;
    }

    public void updateFromDto(EventRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
    }

}
