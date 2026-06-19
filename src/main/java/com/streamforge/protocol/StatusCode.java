package com.streamforge.protocol;

public enum StatusCode {
    OK((short) 0),
    ERROR((short) 1),
    UNKNOWN_REQUEST((short) 2);

    private final short code;

    StatusCode(short code) {
        this.code = code;
    }

    public short code() {
        return code;
    }
}