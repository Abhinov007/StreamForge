package com.streamforge.protocol;

public class Request {
    private final RequestType requestType;
    private final int correlationId;
    private final String clientId;
    private final byte[] body;

    public Request(
            RequestType requestType,
            int correlationId,
            String clientId,
            byte[] body
    ) {
        this.requestType = requestType;
        this.correlationId = correlationId;
        this.clientId = clientId;
        this.body = body;
    }

    public static Request decode(byte[] payload) {
        Decoder decoder = new Decoder(payload);

        short requestTypeCode = decoder.readInt16();
        RequestType requestType = RequestType.fromCode(requestTypeCode);

        int correlationId = decoder.readInt32();
        String clientId = decoder.readString();
        byte[] body = decoder.readRemainingBytes();

        return new Request(requestType, correlationId, clientId, body);
    }

    public RequestType requestType() {
        return requestType;
    }

    public int correlationId() {
        return correlationId;
    }

    public String clientId() {
        return clientId;
    }

    public byte[] body() {
        return body;
    }
}