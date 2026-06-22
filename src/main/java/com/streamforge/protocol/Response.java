package com.streamforge.protocol;

public class Response {
    private final int correlationId;
    private final StatusCode statusCode;
    private final String message;

    public Response(
            int correlationId,
            StatusCode statusCode,
            String message
    ) {
        this.correlationId = correlationId;
        this.statusCode = statusCode;
        this.message = message;
    }

    public byte[] encode() {
        Encoder encoder = new Encoder();

        encoder.writeInt32(correlationId);
        encoder.writeInt16(statusCode.code());
        encoder.writeString(message);

        return encoder.toByteArray();
    }

    public static Response ok(int correlationId, String message) {
        return new Response(correlationId, StatusCode.OK, message);
    }

    public static Response error(int correlationId, String message) {
        return new Response(correlationId, StatusCode.ERROR, message);
    }
}
