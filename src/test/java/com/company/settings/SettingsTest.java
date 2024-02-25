package com.company.settings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class SettingsTest {

    @Test
    @DisplayName("Тест получения порта")
    void getPort() {
        Settings settings = new Settings();
        int port = 15250;

        int result = settings.getPort();
        Assertions.assertEquals(result, port);
    }

    @Test
    @DisplayName("Тест получения хоста")
    void getHost() {
        Settings settings = new Settings();
        String host = "127.0.0.1";

        String result = settings.getHost();
        Assertions.assertEquals(result, host);
    }
}