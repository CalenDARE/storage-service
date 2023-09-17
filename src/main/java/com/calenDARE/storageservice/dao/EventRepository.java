package com.calenDARE.storageservice.dao;

import com.calenDARE.storageservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<List<Event>> getEventsByUser_Id(Long id);
    void deleteByEventId(String eventId);
}
