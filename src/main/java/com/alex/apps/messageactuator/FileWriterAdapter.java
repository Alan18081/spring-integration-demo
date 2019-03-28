package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class FileWriterAdapter {

    @ServiceActivator
    public void writeFile(String content) {
        try {
            Path path = Paths.get("./src/test/write/result.txt");
            if(!Files.exists(path)) {
                Files.createFile(path);
            }
            System.out.println("Hello");
            Files.write(path, (content + "\n").getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
