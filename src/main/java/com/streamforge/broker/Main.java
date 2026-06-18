package com.streamforge.broker;

public class Main {
    public static void main(String[] args) throws Exception {
        BrokerServer server = new BrokerServer(9092);
        server.start();
    }
}