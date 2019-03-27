package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

@MessagingGateway
public interface EnhancedPrinterGateway {

    @Gateway(requestChannel = "directChannel", replyChannel = "resultChannel", headers = { @GatewayHeader(name = "type", expression = "#args[0].firstName")})
    ListenableFuture<String> print(Person person);

    @Gateway
    String uppercase(Person person);
}
