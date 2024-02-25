package com.company.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void beforeEach() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    void testStop() {
        System.out.println("/exit");
        assertEquals("/exit", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testNext() {
        System.out.println("next");
        assertEquals("next", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testConcatStrings() {
        System.out.println("test mainConcat");
        final String original = "next";
        final String argument = "stop";
        final String result = original.concat(argument);
        assertNotEquals(original, result);
    }


}