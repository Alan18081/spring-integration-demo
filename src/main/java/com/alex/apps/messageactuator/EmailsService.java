package com.alex.apps.messageactuator;

import org.springframework.stereotype.Component;

@Component
public class EmailsService {

    public void sendEmail(Person person) {
        System.out.println("Sending email: " + person.getFirstName());
    }

}
