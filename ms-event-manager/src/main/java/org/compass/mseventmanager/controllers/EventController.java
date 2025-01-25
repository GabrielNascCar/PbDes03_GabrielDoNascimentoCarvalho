package org.compass.mseventmanager.controllers;

import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
