package com.streamforge.client;

import com.streamforge.network.FrameReader;
import com.streamforge.network.FrameWriter;
import com.streamforge.protocol.Decoder;
import com.streamforge.protocol.Encoder;
import com.streamforge.protocol.RequestType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws Exception {
        try (
                Socket socket = new Socket("localhost", 9092);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            FrameReader frameReader = new FrameReader(input);
            FrameWriter frameWriter = new FrameWriter(output);

            byte[] pingRequest = buildPingRequest();

            frameWriter.writeFrame(pingRequest);

            byte[] responsePayload = frameReader.readFrame();

            Decoder decoder = new Decoder(responsePayload);

            int correlationId = decoder.readInt32();
            short statusCode = decoder.readInt16();
            String message = decoder.readString();

            System.out.println("Correlation ID: " + correlationId);
            System.out.println("Status Code: " + statusCode);
            System.out.println("Message: " + message);
        }
    }

    private static byte[] buildPingRequest() {
        Encoder encoder = new Encoder();

        encoder.writeInt16(RequestType.PING.code());
        encoder.writeInt32(1001);
        encoder.writeString("test-client");

        return encoder.toByteArray();
    }
}