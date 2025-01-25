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
        return ticketRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Ticket not found for the ID " + id));
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
