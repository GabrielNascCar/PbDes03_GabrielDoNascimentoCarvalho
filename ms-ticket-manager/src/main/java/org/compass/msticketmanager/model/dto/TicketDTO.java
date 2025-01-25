package org.compass.msticketmanager.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.compass.msticketmanager.model.EventData;
import org.compass.msticketmanager.model.Ticket;

@Getter
@Setter
public class TicketDTO {

    private String ticketId;
    private String cpf;
    private String customerName;
    private String customerMail;
    private EventData event;
    private String BRLtotalAmount;
    private String USDtotalAmount;
    private String status;

    public TicketDTO() {}

    public TicketDTO(EventData event, Ticket ticket) {
        this.event = event;
        this.ticketId = ticket.getTicketId();
        this.cpf = ticket.getCpf();
        this.customerName = ticket.getCustomerName();
        this.customerMail = ticket.getCustomerMail();
        this.BRLtotalAmount = ticket.getBRLamount();
        this.USDtotalAmount = ticket.getUSDamount();
        this.status = "Concluido";
    }
}
