package org.compass.msticketmanager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.compass.msticketmanager.model.EventData;
import org.compass.msticketmanager.model.Ticket;
import org.compass.msticketmanager.model.dto.TicketDTO;
import org.compass.msticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody Ticket ticket) throws JsonProcessingException {

        EventData eventData = ticketService.getEventData(ticket.getEventId());

        TicketDTO ticketDTO = new TicketDTO(eventData, ticketService.createTicket(ticket));

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDTO);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String id) {
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<Ticket>> getTicketByCpf(@PathVariable String cpf) {
        List<Ticket> tickets = ticketService.getTicketsByCpf(cpf);
        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tickets);
    }

}
