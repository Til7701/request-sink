package de.holube.request_sink.validation.port;

public record PortRecord(
        int number,
        String serviceName,
        String description,
        PortStatus status
) implements Port {
}
