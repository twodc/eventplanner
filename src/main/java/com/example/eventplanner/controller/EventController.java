package com.example.eventplanner.controller;

import com.example.eventplanner.dto.EventRequestDto;
import com.example.eventplanner.dto.EventResponseDto;
import com.example.eventplanner.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto requestDto) {
        return new ResponseEntity<>(eventService.saveEvent(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getEvents(
            @RequestParam(required = false) String name
    ) {
        return new ResponseEntity<>(eventService.findAllEvents(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.findEventById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public EventResponseDto updateEvent(@RequestBody EventRequestDto requestDto, @PathVariable Long id) {
        eventService.modifyEvent(requestDto, id);
        return eventService.findEventById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@RequestBody EventRequestDto requestDto, @PathVariable Long id) {
        eventService.removeEvent(requestDto.getPassword(), id);
    }

}
