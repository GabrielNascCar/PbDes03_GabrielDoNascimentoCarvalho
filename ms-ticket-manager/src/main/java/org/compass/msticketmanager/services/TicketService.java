package org.compass.msticketmanager.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.compass.msticketmanager.infra.ticket.TicketResource;
import org.compass.msticketmanager.model.Event;
import org.compass.msticketmanager.model.EventData;
import org.compass.msticketmanager.model.Ticket;
import org.compass.msticketmanager.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketResource ticketResource;

    @Transactional
    public Ticket createTicket(Ticket ticket) throws JsonProcessingException {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket getTicket(String id) {
        return ticketRepository.findByTicketIdAndStatus(id, "Completed").orElseThrow(() ->
                new RuntimeException("Ticket not found for the ID " + id));
    }

    public List<Ticket> getTicketsByCpf(String cpf) {
        return ticketRepository.findByCpfAndStatus(cpf, "Completed");
    }

    public List<Ticket> findTicketByEventId(String eventId) {
        return ticketRepository.findByEventIdAndStatus(eventId, "Completed");
    }

    @Transactional
    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketIdAndStatus(ticketId, "Completed")
                .orElseThrow(() -> new RuntimeException("Ticket not found for ID: " + ticketId));
        ticket.setStatus("Deleted");
        ticketRepository.save(ticket);
    }

    public EventData getEventData(String eventId) {

        ResponseEntity<Event> eventData = ticketResource.getEvent(eventId);

        Event event = eventData.getBody();

        return EventData.builder()
                .id(event.getId())
                .eventName(event.getEventName())
                .dateTime(event.getDateTime())
                .cep(event.getCep())
                .logradouro(event.getLogradouro())
                .bairro(event.getBairro())
                .cidade(event.getCidade())
                .uf(event.getUf())
                .build();

    }
}
