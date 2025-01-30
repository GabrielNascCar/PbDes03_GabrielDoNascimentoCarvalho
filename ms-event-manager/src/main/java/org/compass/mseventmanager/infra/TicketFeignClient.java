package org.compass.mseventmanager.infra;

import org.compass.mseventmanager.model.Ticket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ms-ticket-manager", url = "${ticket.service.uri}", path = "/tickets")
public interface TicketFeignClient {

    @GetMapping("/check-tickets-by-event/{eventId}")
    List<Ticket> getTicketsByEvent(@PathVariable String eventId);

}
