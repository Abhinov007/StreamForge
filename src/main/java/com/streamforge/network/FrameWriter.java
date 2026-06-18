package com.streamforge.network;

import java.io.DataOutputStream;
import java.io.IOException;

public class FrameWriter {
    private final DataOutputStream output;

    public FrameWriter(DataOutputStream output) {
        this.output = output;
    }

    public void writeFrame(byte[] payload) throws IOException {
        output.writeInt(payload.length);
        output.write(payload);
        output.flush();
    }
}