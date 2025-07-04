package de.holube.request_sink.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.holube.request_sink.io.IO;
import de.holube.request_sink.io.OutputBuilder;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class Handler implements HttpHandler {

    private static final AtomicInteger requestCounter = new AtomicInteger();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputBuilder ob = new OutputBuilder();

        ob.addMetadataLine("Time", Instant.now().toString());
        ob.addMetadataLine("Method", exchange.getRequestMethod());
        ob.addMetadataLine("URI", exchange.getRequestURI().toString());
        ob.setId(requestCounter.getAndIncrement());

        for (Map.Entry<String, List<String>> entry : exchange.getRequestHeaders().entrySet())
            entry.getValue().forEach(v -> ob.addHeaderLine(entry.getKey(), v));

        try {
            String body = new String(exchange.getRequestBody().readAllBytes());
            ob.setBody(body);
        } catch (IOException _) {
            // just catch it
        }

        IO.println(ob.build());

        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

}