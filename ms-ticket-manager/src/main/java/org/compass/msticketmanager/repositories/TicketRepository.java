package org.compass.msticketmanager.repositories;

import org.compass.msticketmanager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findByTicketIdAndStatus(String ticketId, String status);
    List<Ticket> findByCpfAndStatus(String cpf, String status);
    List<Ticket> findByEventIdAndStatus(String eventId, String status);
}
