package com.company.client;

import com.company.logger.Logger;
import com.company.settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader readerConsole;
    private String clientName;

    private static Settings settings = new Settings();
    private static Logger logger = Logger.getInstance();

    public Client() {
        logger.log("Клиент запущен.");
    }

    public static void main(String[] args) {
        Client client = new Client();

        logger.log("Вы подключились к серверу!");
        logger.log("Для остановки клиента введите: /exit");
        client.run();
    }

    public void run() {
        try {
            clientSocket = new Socket(settings.getHost(), settings.getPort());
            readerConsole = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.addName();
            Thread threadFromServer = messageFromServer();
            threadFromServer.start();
            Thread threadToServer = messageToServer();
            threadToServer.start();
            threadFromServer.join();
            threadToServer.join();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Client.this.clientExit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addName() throws IOException {
        System.out.print("Введите ваше имя: ");
        clientName = readerConsole.readLine();
        if (clientName == null || clientName.isEmpty() || clientName.contains(" ")) {
            clientName = "Client";
        }
        logger.log("Введите ваше имя: " + clientName);
        writer.write("Добро пожаловать " + clientName + "!" + "\n");
        writer.flush();

    }

    public void clientExit() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
                reader.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Thread messageFromServer() {
        return new Thread(() -> {
            String message;
            try {
                while (true) {
                    System.out.print("Введите сообщение: ");
                    message = reader.readLine();
                    if (message.equals("/exit")) {
                        Client.this.clientExit();
                        break;
                    }
                    logger.log(message);
                }
            } catch (IOException e) {
                Client.this.clientExit();
            }
        });
    }

    public Thread messageToServer() {
        return new Thread(() -> {
            while (true) {
                String clientMessage;
                try {
                    clientMessage = readerConsole.readLine();
                    if (clientMessage.equals("/exit")) {
                        writer.write("/exit" + "\n");
                        Client.this.clientExit();
                        logger.log("Клиент " + clientName + " покинул чат.");
                        break;
                    } else {
                        writer.write((clientName + " : >> " + clientMessage + "\n"));
                    }
                    writer.flush();
                } catch (Exception e) {
                    Client.this.clientExit();
                }
            }
        });
    }
}
