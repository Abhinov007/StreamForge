package com.streamforge.protocol;

public enum RequestType {
    PING((short) 1),
    CREATE_TOPIC((short) 2),
    METADATA((short) 3),
    PRODUCE((short) 4),
    FETCH((short) 5);

    private final short code;

    RequestType(short code) {
        this.code = code;
    }

    public short code() {
        return code;
    }

    public static RequestType fromCode(short code) {
        for (RequestType type : values()) {
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown request type: " + code);
    }
}