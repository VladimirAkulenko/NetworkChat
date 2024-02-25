package com.company.server;

import com.company.client.ClientThread;
import com.company.logger.Logger;
import com.company.settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<ClientThread> clients = new ArrayList<>();
    private static Logger logger = Logger.getInstance();

    public Server() {
    }

    public static void main(String[] args) {
        Settings settings = new Settings();
        Server server = new Server();
        server.run(settings.getPort());
    }

    public void run(int port) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            logger.log("Сервер запущен.");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(this, clientSocket);
                clients.add(clientThread);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
                logger.log("Сервер остановлен.");
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ClientThread client :
                clients) {
            client.sendMessage(message);
        }
    }

    public void deleteClient(ClientThread clientThread) {
        clients.remove(clientThread);
    }
}