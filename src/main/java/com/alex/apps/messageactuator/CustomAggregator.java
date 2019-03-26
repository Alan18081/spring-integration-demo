package com.alex.apps.messageactuator;

import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.aggregator.ExpressionEvaluatingCorrelationStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAggregator {

    @Aggregator(inputChannel = "directChannel", outputChannel = "resultChannel")
    public String aggregate(List<Message<?>> group) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Message<?> message : group) {
            System.out.println(message.getPayload());
            stringBuilder.append(message.getPayload().toString());
        }
        System.out.println("Some stuff " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    @ReleaseStrategy
    public boolean releaseChecker(List<Message<?>> messages) {
        return messages.size() > 5;
    }

    @CorrelationStrategy
    public String correlatedBy(@Header("type") String type) {
        return type;
    }
}
