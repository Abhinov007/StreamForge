package com.streamforge.network;

import java.io.DataInputStream;
import java.io.IOException;

public class FrameReader {
    private final DataInputStream input;

    public FrameReader(DataInputStream input) {
        this.input = input;
    }

    public byte[] readFrame() throws IOException {
        int frameSize = input.readInt();

        if (frameSize <= 0) {
            throw new IOException("Invalid frame size: " + frameSize);
        }

        if (frameSize > 10 * 1024 * 1024) {
            throw new IOException("Frame too large: " + frameSize);
        }

        byte[] payload = input.readNBytes(frameSize);

        if (payload.length != frameSize) {
            throw new IOException("Incomplete frame. Expected " + frameSize + " bytes but got " + payload.length);
        }

        return payload;
    }
}