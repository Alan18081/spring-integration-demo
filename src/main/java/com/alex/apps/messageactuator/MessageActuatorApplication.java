package com.alex.apps.messageactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@SpringBootApplication
@Configuration
@IntegrationComponentScan("com.alex.apps.messageactuator")
@ComponentScan
public class MessageActuatorApplication implements ApplicationRunner {

    @Autowired
    private EnhancedPrinterGateway gateway;

    @Autowired
    @Qualifier("directChannel")
    private DirectChannel directChannel;

    @Autowired
    @Qualifier("resultChannel")
    private DirectChannel resultChannel;

    public static void main(String[] args) {
        SpringApplication.run(MessageActuatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        resultChannel.subscribe(m -> {
//            System.out.println("Result: " + m.getPayload());
//        });

        Person alan = new Person("Alan", "Turing");

        Message<Person> message = MessageBuilder
                .withPayload(new Person("Alan", "Turing"))
                .setHeader("replyChannel", resultChannel)
                .build();

        ListenableFuture<String> result = gateway.print(alan);
        result.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(String s) {
                System.out.println("Success callback");
                System.out.println(s);
            }
        });
        System.out.println("New result: " + result);
    }


}
