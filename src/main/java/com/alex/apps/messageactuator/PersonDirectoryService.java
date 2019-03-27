package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.stereotype.Component;

@Component
public class PersonDirectoryService {

    @InboundChannelAdapter
    public Person findNewPerson() {
        return new Person("Elon", "Musk");
    }

}
