package com.company.client;

import com.company.server.Server;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Server server;
    private final Socket clientSocket;
    private final BufferedWriter writer;
    private final BufferedReader reader;


    public ClientThread(Server server, Socket clientSocket) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {

        String clientMessage;
        try {
            clientMessage = reader.readLine();
            try {
                writer.write(clientMessage + "\n");
                writer.flush();
            } catch (IOException ignored) {
            }
            try {
                while (true) {
                    clientMessage = reader.readLine();
                    if (clientMessage.equals(null)) {
                        this.clientExit();
                        break;
                    }
                    System.out.println(clientMessage);
                    server.sendMessageAll(clientMessage);
                }
            } catch (NullPointerException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.clientExit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clientExit() throws IOException {
        if (!clientSocket.isClosed()) {
            clientSocket.close();
            writer.close();
            reader.close();
            server.deleteClient(this);
        }
    }
}