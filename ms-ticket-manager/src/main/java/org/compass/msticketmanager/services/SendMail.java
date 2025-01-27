package org.compass.msticketmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.compass.msticketmanager.model.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMail {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void send(Message message) throws JsonProcessingException {
        var json = convertIntoJson(message);
        rabbitTemplate.convertAndSend(queue.getName(), json);
    }

    private String convertIntoJson(Message message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(message);
        return json;
    }

}
