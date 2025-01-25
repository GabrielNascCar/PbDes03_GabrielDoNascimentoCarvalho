package org.compass.msticketmanager.infra.ticket;

import org.compass.msticketmanager.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-event-manager", url = "http://localhost:8082", path = "/events")
public interface TicketResource {

    @GetMapping("/get-ticket/{id}")
    ResponseEntity<Event> getEvent(@PathVariable String id);
}
