package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface FilesGateway {

    @Gateway
    String getData();

}
