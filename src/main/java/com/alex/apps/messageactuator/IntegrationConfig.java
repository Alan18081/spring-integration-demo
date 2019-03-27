package com.alex.apps.messageactuator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.MapBuilder;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;

@Configuration
@ComponentScan
@IntegrationComponentScan("com.alex.apps.messageactuator")
@EnableIntegration
public class IntegrationConfig {

    @Autowired
    private PersonDirectoryService personDirectoryService;

    @Bean
    public DirectChannel directChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel resultChannel() {
        return new DirectChannel();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }
    @Bean
    public IntegrationFlow personsFlow() {
        return IntegrationFlows.from(personDirectoryService, "findNewPerson")
                .channel(MessageChannels.queue())
                .handle("emailsService", "sendEmail")
                .get();
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(EnhancedPrinterGateway.class)
                .channel("directChannel")
                .handle((Person person, MessageHeaders h) -> {
                    System.out.println(h.get("type"));
                    return person;
                })
                .handle("uppercaseService", "execute")
                .channel("resultChannel")
                .get();
    }

}
