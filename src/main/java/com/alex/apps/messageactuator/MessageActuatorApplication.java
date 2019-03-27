package com.alex.apps.messageactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

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
    private PrinterGateway printerGateway;

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
        resultChannel.subscribe(m -> {
            for(Map.Entry<String, Object> entry : m.getHeaders().entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        });

        Person[] names = {new Person("Alan", "Turing"), new Person("Elon", "Musk"), new Person("Thomas", "Edison")};

        for(Person name : names) {
            Message<Person> message = MessageBuilder.withPayload(name).setHeader("privateKey", "12345").build();
            System.out.println(message.getPayload());
            directChannel.send(message);
        }

        List<Future<Message<Integer>>> futures = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Message<Integer> message = MessageBuilder.withPayload(i).setPriority(i).build();
            futures.add(this.printerGateway.print(message));
        }

        for(Future<Message<Integer>> item : futures) {
            System.out.println(item.get().getPayload());
        }
    }


}
