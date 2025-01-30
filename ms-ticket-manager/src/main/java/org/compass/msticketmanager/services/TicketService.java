package org.compass.msticketmanager.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.compass.msticketmanager.exceptions.EventNotFoundException;
import org.compass.msticketmanager.exceptions.TicketNotFoundException;
import org.compass.msticketmanager.infra.EventFeignClient;
import org.compass.msticketmanager.model.Event;
import org.compass.msticketmanager.model.EventData;
import org.compass.msticketmanager.model.Message;
import org.compass.msticketmanager.model.Ticket;
import org.compass.msticketmanager.model.dto.TicketDTO;
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
    private EventFeignClient eventFeignClient;

    @Autowired
    private SendMail sendMail;

    @Transactional
    public TicketDTO createTicket(Ticket ticket) throws JsonProcessingException {
        try {
            EventData eventData = getEventData(ticket.getEventId());
            TicketDTO ticketDTO = new TicketDTO(eventData, ticketRepository.save(ticket));
            Message message = new Message(ticket.getCustomerMail(), ticket.getCustomerName(), ticket.getEventName());
            sendMail.send(message);
            return ticketDTO;
        } catch (FeignException e) {
            throw new EventNotFoundException("Event not found for ID: " + ticket.getEventId());
        }
    }

    @Transactional
    public Ticket getTicket(String id) {
        return ticketRepository.findByTicketIdAndStatus(id, "Completed").orElseThrow(() ->
                new TicketNotFoundException("Ticket not found for the ID " + id));
    }

    public List<Ticket> getTicketsByCpf(String cpf) {
        return ticketRepository.findByCpfAndStatus(cpf, "Completed");
    }

    public List<Ticket> findTicketByEventId(String eventId) {
        return ticketRepository.findByEventIdAndStatus(eventId, "Completed");
    }

    @Transactional
    public Ticket updateTicket(String id, Ticket ticket) {
        Ticket t = getTicket(id);
        t.setCpf(ticket.getCpf());
        t.setCustomerMail(ticket.getCustomerMail());
        t.setCustomerName(ticket.getCustomerName());
        return ticketRepository.save(t);
    }

    @Transactional
    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketIdAndStatus(ticketId, "Completed")
                .orElseThrow(() -> new RuntimeException("Ticket not found for ID: " + ticketId));
        ticket.setStatus("Deleted");
        ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpfAndStatus(cpf, "Completed");
        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for CPF: " + cpf);
        }
        for (Ticket ticket : tickets) {
            ticket.setStatus("Deleted");
        }
        ticketRepository.saveAll(tickets);
    }

    public EventData getEventData(String eventId) {

        ResponseEntity<Event> eventData = eventFeignClient.getEvent(eventId);

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
