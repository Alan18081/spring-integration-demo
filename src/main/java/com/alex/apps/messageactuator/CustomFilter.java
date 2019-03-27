package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

public class CustomFilter  {

    @Filter
    public boolean headerFilter(Message<?> message) {
        System.out.println("Filtering");
        return Integer.parseInt(message.getPayload().toString()) > 5;
    }

}
