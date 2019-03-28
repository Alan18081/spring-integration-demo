package com.alex.apps.messageactuator;

import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileAdapter {

    @InboundChannelAdapter
    public File readFile() {
        return new File("./src/test/sample.txt");
    }

}
