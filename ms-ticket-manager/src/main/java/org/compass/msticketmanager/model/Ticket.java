package org.compass.msticketmanager.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    @JsonDeserialize(using = StringDeserializer.class)
    private String BRLamount;
    @JsonDeserialize(using = StringDeserializer.class)
    private String USDamount;
}
