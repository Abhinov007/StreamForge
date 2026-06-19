package com.streamforge.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Decoder {
    private final ByteBuffer buffer;

    public Decoder(byte[] data) {
        this.buffer = ByteBuffer.wrap(data);
    }

    public short readInt16() {
        ensureRemaining(2);
        return buffer.getShort();
    }

    public int readInt32() {
        ensureRemaining(4);
        return buffer.getInt();
    }

    public String readString() {
        short length = readInt16();

        if (length < 0) {
            return null;
        }

        ensureRemaining(length);

        byte[] bytes = new byte[length];
        buffer.get(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] readRemainingBytes() {
        byte[] remaining = new byte[buffer.remaining()];
        buffer.get(remaining);
        return remaining;
    }

    private void ensureRemaining(int bytes) {
        if (buffer.remaining() < bytes) {
            throw new IllegalArgumentException("Not enough bytes in buffer");
        }
    }
}