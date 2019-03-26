package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class NumericPrintService {

    @ServiceActivator(inputChannel = "integerChannel", poller = @Poller(maxMessagesPerPoll = "10"))
    public void print(Message<?> message) {
        System.out.println("Numeric handler: " + message.getPayload());
    }

}
