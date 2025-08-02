package com.example.eventplanner.service;

import com.example.eventplanner.dto.EventRequestDto;
import com.example.eventplanner.dto.EventResponseDto;
import com.example.eventplanner.entity.Event;
import com.example.eventplanner.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    // 일정 생성
    @Transactional
    public EventResponseDto saveEvent(EventRequestDto requestDto) {
        Event event = new Event(
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getName(), requestDto.getPassword());
        Event savedEvent = eventRepository.save(event);

        return new EventResponseDto(savedEvent);
    }

    // 전체 일정 조회
    @Transactional(readOnly = true)
    public List<EventResponseDto> findAllEvents(String name) {
        List<Event> events = (name == null)
                ? eventRepository.findAllByOrderByModifiedAtDesc()
                : eventRepository.findByNameOrderByModifiedAtDesc(name);

        if (events.isEmpty()) {
            throw new EntityNotFoundException("등록된 일정이 없습니다.");
        }

        return events.stream().map(EventResponseDto::new).toList();
    }

    // 선택 일정 조회
    @Transactional(readOnly = true)
    public EventResponseDto findEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        return new EventResponseDto(event);
    }

    // 일정 수정
    @Transactional
    public void modifyEvent(EventRequestDto requestDto, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        if (event.getPassword().equals(requestDto.getPassword())) {
            event.updateEventFromDto(requestDto);
            eventRepository.save(event);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

    // 일정 삭제
    @Transactional
    public void removeEvent(String password, Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        if (event.getPassword().equals(password)) {
            eventRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

}
