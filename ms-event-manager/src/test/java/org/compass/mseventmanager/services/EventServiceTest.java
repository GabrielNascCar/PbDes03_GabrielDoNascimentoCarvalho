package org.compass.mseventmanager.services;

import org.compass.mseventmanager.exceptions.EventDeletionException;
import org.compass.mseventmanager.exceptions.EventNotFoundException;
import org.compass.mseventmanager.infra.TicketFeignClient;
import org.compass.mseventmanager.infra.ZipCodeClient;
import org.compass.mseventmanager.model.Address;
import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.model.Ticket;
import org.compass.mseventmanager.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Test
    void testGetEventById_NotFound() {
        when(eventRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.getEventById("1");
        });
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = Arrays.asList(new Event(), new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEvent() {
        Event existingEvent = new Event();
        existingEvent.setId("1");
        existingEvent.setEventName("Show");

        Event updatedEvent = new Event();
        updatedEvent.setEventName("Event");
        updatedEvent.setDateTime(LocalDateTime.now());

        when(eventRepository.findById("1")).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(existingEvent);

        Event result = eventService.updateEvent("1", updatedEvent);

        assertNotNull(result);
        assertEquals("Event", result.getEventName());
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void testDeleteEvent() {
        Event event = new Event();
        event.setId("1");

        when(eventRepository.findById("1")).thenReturn(Optional.of(event));
        when(ticketFeignClient.getTicketsByEvent("1")).thenReturn(Arrays.asList());

        eventService.deleteEvent("1");

        verify(eventRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteEvent_TicketsSold() {
        Event event = new Event();
        event.setId("1");

        when(eventRepository.findById("1")).thenReturn(Optional.of(event));
        when(ticketFeignClient.getTicketsByEvent("1")).thenReturn(Arrays.asList(new Ticket()));

        assertThrows(EventDeletionException.class, () -> {
            eventService.deleteEvent("1");
        });
    }

    @Test
    void testGetAllEventsSorted() {
        Event event1 = new Event();
        event1.setEventName("B Event");

        Event event2 = new Event();
        event2.setEventName("A Event");

        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEventsSorted();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("A Event", result.get(0).getEventName());
        verify(eventRepository, times(1)).findAll();
    }

}
