package de.holube.request_sink.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.holube.request_sink.io.IO;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("Received Request: \n");
        sb.append("Time: ").append(Instant.now().toString()).append("\n");
        sb.append("Method: ").append(exchange.getRequestMethod()).append("\n");
        sb.append("URI: ").append(exchange.getRequestURI()).append("\n");
        sb.append("========== Request Start ==========\n");
        appendHeaders(sb, exchange.getRequestHeaders());
        appendBody(sb, exchange.getRequestBody());
        sb.append("========== Request End ==========\n");

        IO.println(sb.toString());

        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

    private static void appendHeaders(StringBuilder sb, Map<String, List<String>> headers) {
        if (headers.isEmpty()) {
            sb.append("---------- No headers in request ----------\n");
            return;
        }

        sb.append("---------- Header Start ----------\n");
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            entry.getValue().forEach(v ->
                    sb.append(entry.getKey()).append(": ").append(v).append("\n")
            );
        }
        sb.append("---------- Header End ----------\n");
    }

    private static void appendBody(StringBuilder sb, InputStream in) {
        String body = "";
        try {
            body = new String(in.readAllBytes());
        } catch (IOException _) {
            IO.println("---------- Could not read body ----------");
        }

        if (body.isEmpty()) {
            sb.append("---------- No body in request ----------\n");
        } else {
            sb
                    .append("---------- Body Start ----------\n")
                    .append(body)
                    .append("---------- Body End ----------\n");
        }
    }

}