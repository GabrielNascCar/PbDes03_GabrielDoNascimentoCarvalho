package org.compass.mseventmanager.services;

import org.compass.mseventmanager.infra.TicketFeignClient;
import org.compass.mseventmanager.infra.ZipCodeClient;
import org.compass.mseventmanager.model.Address;
import org.compass.mseventmanager.model.Event;
import org.compass.mseventmanager.model.Ticket;
import org.compass.mseventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketFeignClient ticketFeignClient;

    @Autowired
    private ZipCodeClient zipCodeClient;

    public Event createEvent(Event event) {

        if(event.getCep() != null) {
            Address address = zipCodeClient.getAddress(event.getCep());

            if(address != null) {
                event.setUf(address.getUf());
                event.setCidade(event.getCidade());
                event.setBairro(event.getBairro());
                event.setLogradouro(event.getLogradouro());
            }

        }

        return eventRepository.save(event);
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).get();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(String id, Event event) {
        Event event1 = getEventById(id);
        event1.setEventName(event.getEventName());
        event1.setDateTime(event.getDateTime());
        return eventRepository.save(event1);
    }

    public void deleteEvent(String id) {

        List<Ticket> tickets = ticketFeignClient.getTicketsByEvent(id);

        if (!tickets.isEmpty()) {
            throw new RuntimeException("Cannot delete event. Tickets have been sold.");
        }

        Event event = eventRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Event not found for ID: " + id));

        eventRepository.deleteById(id);
    }

    public List<Event> getAllEventsSorted() {
        List<Event> events = eventRepository.findAll();
        events.sort(Comparator.comparing(Event::getEventName));
        return events;
    }

}
