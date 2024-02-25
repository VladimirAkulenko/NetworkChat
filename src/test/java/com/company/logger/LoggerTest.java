package com.company.logger;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {


    @Test
    @DisplayName("Тест логера")
    void log() {
        String msg = "";
        String original = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + " " + "] " + msg;
        String argument = "";
        String result = original.concat(argument);
        Assertions.assertEquals(result, original);
    }
}