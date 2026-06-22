package com.streamforge.broker;

import com.streamforge.handlers.RequestRouter;
import com.streamforge.network.FrameReader;
import com.streamforge.network.FrameWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final RequestRouter requestRouter;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.requestRouter = new RequestRouter();
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

                byte[] responsePayload = requestRouter.handle(requestPayload);

                frameWriter.writeFrame(responsePayload);
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }
}
