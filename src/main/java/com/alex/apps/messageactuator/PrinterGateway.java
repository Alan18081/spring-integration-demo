package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import java.util.concurrent.Future;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PrinterGateway {

    @Gateway(requestChannel = "inputChannel")
    public Future<Message<Integer>> print(Message<?> message);

}
