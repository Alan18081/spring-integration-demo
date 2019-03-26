package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomFilter {

//    @Filter(inputChannel = "directChannel", outputChannel = "resultChannel")
    public boolean filter(Message<?> message) {
        System.out.println("Filtering");
        return Integer.parseInt(message.getPayload().toString()) > 5;
    }

}
