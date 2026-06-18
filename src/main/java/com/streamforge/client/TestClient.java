package com.streamforge.client;

import com.streamforge.network.FrameReader;
import com.streamforge.network.FrameWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestClient {
    public static void main(String[] args) throws Exception {
        try (
                Socket socket = new Socket("localhost", 9092);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            FrameReader frameReader = new FrameReader(input);
            FrameWriter frameWriter = new FrameWriter(output);

            String message = "hello-streamforge";

            frameWriter.writeFrame(message.getBytes(StandardCharsets.UTF_8));

            byte[] responsePayload = frameReader.readFrame();

            String response = new String(responsePayload, StandardCharsets.UTF_8);

            System.out.println("Broker response: " + response);
        }
    }
}