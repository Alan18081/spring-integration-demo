package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

public class CustomTransformer {

    @Transformer
    public Message<?> transform(Message<?> message) {

    }

}
