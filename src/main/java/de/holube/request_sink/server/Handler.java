package de.holube.request_sink.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.holube.request_sink.io.OutputBuilder;
import picocli.CommandLine;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class receives requests and uses the {@link OutputBuilder} to construct the output.
 * Each request uses only one print statement to reduce the impact of parallel requests.
 * <p>
 * After the request is printed, the set status code is returned and the exchange closed.
 */
public final class Handler implements HttpHandler {

    private static final AtomicInteger requestCounter = new AtomicInteger();

    private final int statusCode;

    /**
     * Constructs a new handler to use with an {@link com.sun.net.httpserver.HttpServer}.
     *
     * @param statusCode the status code to return to requests
     */
    public Handler(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CommandLine.tracer().debug("Received request");
        OutputBuilder ob = new OutputBuilder();

        // Metadata
        ob.setId(requestCounter.getAndIncrement());
        ob.addMetadataLine("Time", Instant.now().toString());
        ob.addMetadataLine("Method", exchange.getRequestMethod());
        ob.addMetadataLine("URI", exchange.getRequestURI().toString());

        // Headers
        for (Map.Entry<String, List<String>> entry : exchange.getRequestHeaders().entrySet())
            entry.getValue().forEach(v -> ob.addHeaderLine(entry.getKey(), v));

        // Body
        try {
            String body = new String(exchange.getRequestBody().readAllBytes());
            ob.setBody(body);
        } catch (IOException e) {
            CommandLine.tracer().debug("Failed to read request body", e);
        }

        IO.println(ob.build());

        exchange.sendResponseHeaders(statusCode, -1);
        exchange.close();
        CommandLine.tracer().debug("Finished handling request.");
    }

}
