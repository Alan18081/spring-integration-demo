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
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.MapBuilder;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;

@Configuration
@ComponentScan
@IntegrationComponentScan("com.alex.apps.messageactuator")
@EnableIntegration
public class IntegrationConfig {

    @Autowired
    private LoggingChannelInterceptor loggingChannelInterceptor;

    @Bean
    @BridgeFrom(value = "inputChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "10"))
    public PublishSubscribeChannel outputChannel() {
        return new PublishSubscribeChannel(executor());
    }

    @Bean
    @BridgeTo(value = "outputChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "10"))
    public QueueChannel inputChannel() {
        QueueChannel channel = new QueueChannel();
        channel.addInterceptor(loggingChannelInterceptor);
        return channel;
    }

    @Bean
    public TaskExecutor executor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public CustomTransformer customTransformer() {
        return new CustomTransformer();
    }

//    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel", poller = @Poller(maxMessagesPerPoll = "10"))
//    @Bean
//    public AbstractMessageRouter payloadTypeRouter() {
//        return new CustomRouter();
//    }

    @Bean
    public QueueChannel integerChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel stringChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public DirectChannel directChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel resultChannel() {
        return new DirectChannel();
    }

    @Bean
    public ObjectToJsonTransformer objectToStringTransformer() {
        return new ObjectToJsonTransformer();
    }

    @Bean
    public RecipientListRouter customRouter() {
        RecipientListRouter router = new RecipientListRouter();
        router.addRecipient("integerChannel");
        router.addRecipient("stringChannel");
        return router;
    }

    @Bean
    public AbstractMessageRouter msgRouter() {
        return new CustomRouter();
    }

    @Bean
    public IntegrationFlow integer() {
        return IntegrationFlows.from("integerChannel")
                .handle(m -> System.out.println(m.getPayload()))
                .get();
    }
    @Bean
    public IntegrationFlow string() {
        return IntegrationFlows.from("stringChannel")
                .handle(m -> System.out.println("String" + m.getPayload()))
                .get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }

    @Bean
    public IntegrationFlow messageSender() {
        return IntegrationFlows.from("directChannel")
                .headerFilter("privateKey")
                .enrichHeaders(new HashMap<String, Object>() {{ put("type", "person"); put("index", "1"); }})
                .transform(objectToStringTransformer())
                .route(m -> m.getPayload(), r -> )
                .get();
    }
}
