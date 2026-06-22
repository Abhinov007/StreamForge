package com.streamforge.handlers;

import com.streamforge.protocol.Request;
import com.streamforge.protocol.Response;

public class PingHandler {
    public Response handle(Request request) {
        System.out.println("PING received from client_id=" + request.clientId());

        return Response.ok(request.correlationId(), "PONG");
    }
}
