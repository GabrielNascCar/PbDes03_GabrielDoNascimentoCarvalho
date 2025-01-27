package org.compass.msticketmanager.model;

import lombok.Data;

@Data
public class Message {

    private String message;
    private String email;

    public Message(String email, String name, String eventName) {
        this.email = email;
        this.message = "Hello, " + name + "!\n" +
                "\n" +
                "We are very happy to inform you that your purchase has been successfully completed!" +
                "\n" +
                "Purchase Details:\n" +
                "\n" +
                "Event: " + eventName + "\n" +
                "\n" +
                "Please keep this email as confirmation of your purchase. If you have any questions or need more information, do not hesitate to contact us.\n" +
                "\n" +
                "Thank you for your trust and we hope you enjoy the event to the fullest!\n" +
                "\n" +
                "Best regards, Gabriel do nascimento carvalho";
    }

}
