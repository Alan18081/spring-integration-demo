package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FilePrinter {

    @Transformer
    public String printFile(File file) {
        Path path = Paths.get(file.toURI());

        try {
            List<String> content = Files.readAllLines(path);
            StringBuilder stringBuilder = new StringBuilder();
            for(String line : content) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
