package com.company.server;

import com.company.logger.Logger;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    ServerSocket server = new ServerSocket(15250);
    private static final Logger log = Logger.getInstance();
    String host;
    int port;

    ServerTest() throws IOException {
    }


    @Test
    void main() {
        // считываем настройки из файла
        try (FileReader reader = new FileReader("settings.txt")) {
            Properties props = new Properties();
            props.load(reader);
            port = Integer.parseInt(props.getProperty("PORT"));
            host = props.getProperty("HOST");
        } catch (IOException ex) {
            log.log(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        assertEquals(15250, port);
        assertEquals("127.0.0.1", host);

    }

    @Test
    void testPort() {
        int expected = 15250;
        int actual = server.getLocalPort();
        assertEquals(expected, actual);
    }
}