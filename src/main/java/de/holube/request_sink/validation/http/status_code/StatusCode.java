package de.holube.request_sink.validation.http.status_code;

record StatusCode(
        int code,
        String description,
        HttpStatusCodeStatus status
) implements HttpStatusCode {
}
