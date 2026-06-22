package com.streamforge.handlers;

import com.streamforge.protocol.Request;
import com.streamforge.protocol.RequestType;
import com.streamforge.protocol.Response;

public class RequestRouter {
    private final PingHandler pingHandler;

    public RequestRouter() {
        this.pingHandler = new PingHandler();
    }

    public byte[] handle(byte[] payload) {
        try {
            Request request = Request.decode(payload);

            System.out.printf(
                    "Received request: type=%s correlation_id=%d client_id=%s%n",
                    request.requestType(),
                    request.correlationId(),
                    request.clientId());

            if (request.requestType() == RequestType.PING) {
                return pingHandler.handle(request).encode();
            }

            return Response.error(
                    request.correlationId(),
                    "Unsupported request type: " + request.requestType()).encode();

        } catch (Exception e) {
            System.err.println("Failed to handle request: " + e.getMessage());
            return Response.error(-1, "Bad request: " + e.getMessage()).encode();
        }
    }
}