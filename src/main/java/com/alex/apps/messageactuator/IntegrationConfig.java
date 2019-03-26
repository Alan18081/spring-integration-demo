package com.alex.apps.messageactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.messaging.Message;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel", poller = @Poller(maxMessagesPerPoll = "10"))
    @Bean
    public AbstractMessageRouter payloadTypeRouter() {
        return new CustomRouter();
    }

    @Bean
    public QueueChannel integerChannel() {
        return new QueueChannel();
    }

    @Bean
    public QueueChannel stringChannel() {
        return new QueueChannel();
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
    public IntegrationFlow messageSender() {
        return IntegrationFlows.from("directChannel")
                .filter((Message<?> message) -> message.getPayload().toString().length() > 5)
                .transform(customTransformer())
                .channel("resultChannel")
                .get();
    }
}
