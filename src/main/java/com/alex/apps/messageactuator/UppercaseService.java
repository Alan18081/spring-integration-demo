package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class UppercaseService {

    @ServiceActivator
    public String execute(Person person) {
        return (person.getFirstName() + " " + person.getLastName()).toUpperCase();
    }

}
