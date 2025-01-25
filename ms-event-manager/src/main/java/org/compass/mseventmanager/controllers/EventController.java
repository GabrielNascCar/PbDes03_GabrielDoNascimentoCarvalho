package org.compass.mseventmanager.controllers;

import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event event1 = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event1);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable String id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

}
