package com.company.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {
    private static Logger logger;

    private Logger() {
    }

    public void log(String message) {
        String log = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + " " + "] " + message + "\n";
        System.out.print(log);

        try (FileWriter writer = new FileWriter("file.log", true)) {
            writer.write(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
}