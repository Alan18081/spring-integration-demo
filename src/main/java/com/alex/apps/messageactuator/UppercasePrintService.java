package com.alex.apps.messageactuator;


import org.springframework.core.annotation.Order;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UppercasePrintService {

    @ServiceActivator(inputChannel = "inputChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "10"))
    public void print(Message<String> message) {
        System.out.println(message.getPayload().toUpperCase());
    }

}
