package org.compass.msticketmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.compass.msticketmanager.infra.EventFeignClient;
import org.compass.msticketmanager.model.Event;
import org.compass.msticketmanager.model.Message;
import org.compass.msticketmanager.model.Ticket;
import org.compass.msticketmanager.model.dto.TicketDTO;
import org.compass.msticketmanager.repositories.TicketRepository;
import org.compass.msticketmanager.services.SendMail;
import org.compass.msticketmanager.services.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventFeignClient eventFeignClient;

    @Mock
    private SendMail sendMail;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testCreateTicket() throws JsonProcessingException {
        LocalDateTime dateTime = LocalDateTime.parse("2025-01-01T10:00:00");

        Ticket ticket = new Ticket();
        ticket.setEventId("1");
        ticket.setCustomerMail("gabriel@gmail.com");
        ticket.setCustomerName("gabriel nascimento");
        ticket.setEventName("Show gabriel");

        Event event = new Event();
        event.setId("1");
        event.setEventName("Show gabriel");
        event.setDateTime(dateTime);
        event.setCep("12345-678");
        event.setLogradouro("street A");
        event.setBairro("neighborhood B");
        event.setCidade("city C");
        event.setUf("UF");

        when(eventFeignClient.getEvent("1")).thenReturn(ResponseEntity.ok(event));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDTO result = ticketService.createTicket(ticket);

        assertNotNull(result);
        assertEquals("1", result.getEvent().getId());
        verify(ticketRepository, times(1)).save(ticket);
        verify(sendMail, times(1)).send(any(Message.class));
    }

    @Test
    void testGetTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setStatus("Completed");

        when(ticketRepository.findByTicketIdAndStatus("1", "Completed"))
                .thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicket("1");

        assertNotNull(result);
        assertEquals("1", result.getTicketId());
        verify(ticketRepository, times(1)).findByTicketIdAndStatus("1", "Completed");
    }

    @Test
    void testUpdateTicket() {
        Ticket existingTicket = new Ticket();
        existingTicket.setTicketId("1");
        existingTicket.setStatus("Completed");

        Ticket updatedTicket = new Ticket();
        updatedTicket.setCpf("12345678900");
        updatedTicket.setCustomerMail("gabriel@gmail.com");
        updatedTicket.setCustomerName("gabriel nascimento");

        when(ticketRepository.findByTicketIdAndStatus("1", "Completed"))
                .thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        Ticket result = ticketService.updateTicket("1", updatedTicket);

        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        assertEquals("gabriel@gmail.com", result.getCustomerMail());
        assertEquals("gabriel nascimento", result.getCustomerName());
        verify(ticketRepository, times(1)).save(existingTicket);
    }

    @Test
    void testGetTicketsByCpf() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(ticketRepository.findByCpfAndStatus("12345678900", "Completed"))
                .thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsByCpf("12345678900");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findByCpfAndStatus("12345678900", "Completed");
    }

}