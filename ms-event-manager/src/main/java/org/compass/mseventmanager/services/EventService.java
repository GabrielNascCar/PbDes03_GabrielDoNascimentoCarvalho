package org.compass.mseventmanager.services;

import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).get();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(String id, Event event) {
        Event event1 = getEventById(id);
        event1.setEventName(event.getEventName());
        event1.setDateTime(event.getDateTime());
        return eventRepository.save(event1);
    }

}
