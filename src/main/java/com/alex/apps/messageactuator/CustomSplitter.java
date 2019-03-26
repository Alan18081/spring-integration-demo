package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Component
public class CustomSplitter {

//    @Splitter(inputChannel = "directChannel", outputChannel = "resultChannel")
    public Collection<String> split(Message<?> message) {
        return Arrays.asList(message.getPayload().toString().split(" "));
    }

}
