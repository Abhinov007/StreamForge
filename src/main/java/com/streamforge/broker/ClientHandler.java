package com.streamforge.broker;

import com.streamforge.network.FrameReader;
import com.streamforge.network.FrameWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                Socket client = socket;
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream())
        ) {
            FrameReader frameReader = new FrameReader(input);
            FrameWriter frameWriter = new FrameWriter(output);

            while (true) {
                byte[] requestPayload = frameReader.readFrame();

                String request = new String(requestPayload, StandardCharsets.UTF_8);

                System.out.println("Received request: " + request);

                String response = "ACK: " + request;

                frameWriter.writeFrame(response.getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception e) {
            System.out.println("Client disconnected/error: " + e.getMessage());
        }
    }
}