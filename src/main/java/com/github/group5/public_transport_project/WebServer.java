package com.github.group5.public_transport_project;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {
    private HttpServer server;
    private final int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Enkel route: GET /
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String body = "Hei fra WebServer på port " + port + "!";
                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
                exchange.sendResponseHeaders(200, body.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(body.getBytes());
                }
            }
        });

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("WebServer startet på http://localhost:" + port + "/");
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("WebServer stoppet.");
        }
    }
}
