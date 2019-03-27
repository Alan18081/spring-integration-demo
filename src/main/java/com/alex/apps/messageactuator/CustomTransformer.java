package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class CustomTransformer {

//    @Transformer(inputChannel = "directChannel", outputChannel = "resultChannel")
    public Message<String> transform(Message<String> message) {
        String[] tokens = message.getPayload().split(" ");
        return MessageBuilder.withPayload(tokens[1] + ", " + tokens[0]).build();
    }

}
