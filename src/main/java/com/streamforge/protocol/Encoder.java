package com.streamforge.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Encoder {
    private final ByteArrayOutputStream byteStream;
    private final DataOutputStream output;

    public Encoder() {
        this.byteStream = new ByteArrayOutputStream();
        this.output = new DataOutputStream(byteStream);
    }

    public void writeInt16(short value) {
        try {
            output.writeShort(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInt32(int value) {
        try {
            output.writeInt(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeString(String value) {
        try {
            if (value == null) {
                output.writeShort(-1);
                return;
            }

            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            output.writeShort(bytes.length);
            output.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toByteArray() {
        try {
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return byteStream.toByteArray();
    }
}