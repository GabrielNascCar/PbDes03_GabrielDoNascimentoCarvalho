package org.compass.mseventmanager.services;

import org.compass.mseventmanager.infra.TicketFeignClient;
import org.compass.mseventmanager.infra.ZipCodeClient;
import org.compass.mseventmanager.model.Address;
import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketFeignClient ticketFeignClient;

    @Mock
    private ZipCodeClient zipCodeClient;

    @InjectMocks
    private EventService eventService;

    @Test
    void testCreateEvent() {
        Event event = new Event();
        event.setCep("12345-678");

        Address address = new Address();
        address.setUf("SP");
        address.setLocalidade("São Paulo");
        address.setBairro("Centro");
        address.setLogradouro("Rua A");

        when(zipCodeClient.getAddress("12345-678")).thenReturn(address);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertNotNull(result);
        assertEquals("SP", result.getUf());
        assertEquals("São Paulo", result.getCidade());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testGetEventById() {
        Event event = new Event();
        event.setId("1");

        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        Event result = eventService.getEventById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(eventRepository, times(1)).findById("1");
    }

}
