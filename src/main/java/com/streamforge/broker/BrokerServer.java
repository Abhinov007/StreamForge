package com.streamforge.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer {
    private final int port;

    public BrokerServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("StreamForge broker started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Client connected: " + socket.getRemoteSocketAddress());

                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }
        }
    }
}