package com.example.eventplanner.controller;

import com.example.eventplanner.dto.EventRequestDto;
import com.example.eventplanner.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequestDto requestDto) {
        try {
            return new ResponseEntity<>(eventService.saveEvent(requestDto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 입력 항목이 누락되었습니다.");
        }

    }

    @GetMapping
    public ResponseEntity<?> getEvents(
            @RequestParam(required = false) String name
    ) {
        try {
            return new ResponseEntity<>(eventService.findAllEvents(name), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("등록된 일정이 없습니다.");
        }

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Long eventId) {
        try {
            return new ResponseEntity<>(eventService.findEventById(eventId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@RequestBody EventRequestDto requestDto, @PathVariable Long eventId) {
        try {
            eventService.modifyEvent(requestDto, eventId);
            return new ResponseEntity<>(eventService.findEventById(eventId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@RequestBody EventRequestDto requestDto, @PathVariable Long eventId) {
        try {
            eventService.removeEvent(requestDto.getPassword(), eventId);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
