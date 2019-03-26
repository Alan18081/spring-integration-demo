package com.alex.apps.messageactuator;


import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class PrintService {

    @ServiceActivator(inputChannel = "stringChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "10"))
    public Message<String> print(Message<String> message) {
        System.out.println(message.getPayload());
        return MessageBuilder.withPayload("Message payload").build();
    }

}
