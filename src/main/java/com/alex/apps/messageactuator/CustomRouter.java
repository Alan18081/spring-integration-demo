package com.alex.apps.messageactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.ArrayList;
import java.util.Collection;

public class CustomRouter extends AbstractMessageRouter {

    @Autowired
    @Qualifier("integerChannel")
    private MessageChannel integerChannel;

    @Autowired
    @Qualifier("stringChannel")
    private MessageChannel stringChannel;

    @Override
    protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        Collection<MessageChannel> channels = new ArrayList<>();
        if(message.getPayload().getClass().equals(Integer.class)) {
            channels.add(integerChannel);
        } else if(message.getPayload().getClass().equals(String.class)) {
            channels.add(stringChannel);
        }

        return channels;
    }
}
