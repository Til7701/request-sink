package de.holube.request_sink.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    HttpExchange exchange;

    @Test
    void testResponse() throws IOException {
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(exchange.getRequestURI()).thenReturn(java.net.URI.create("/test"));
        when(exchange.getRequestHeaders()).thenReturn(new Headers(Map.of(
                "Content-Type", List.of("application/json"),
                "User-Agent", List.of("TestAgent/1.0")
        )));
        when(exchange.getRequestBody()).thenReturn(java.io.ByteArrayInputStream.nullInputStream());
        Handler handler = new Handler(42);

        handler.handle(exchange);

        verify(exchange).sendResponseHeaders(42, -1);
        verify(exchange).close();
    }

}
