package com.example.eventplanner.repository;

import com.example.eventplanner.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
