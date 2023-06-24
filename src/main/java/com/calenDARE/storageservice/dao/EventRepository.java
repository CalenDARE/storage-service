package com.calenDARE.storageservice.dao;

import com.calenDARE.storageservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
