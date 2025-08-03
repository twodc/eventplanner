package com.example.eventplanner.repository;

import com.example.eventplanner.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByModifiedAtDesc();
    List<Event> findByNameOrderByModifiedAtDesc(String name);
    @Query("select e from Event e left join fetch e.comments where e.eventId = :eventId")
    Optional<Event> findByWithComments(@Param("eventId") Long eventId);
}