package de.holube.request_sink.validation.http.status_code;

public sealed interface HttpStatusCode permits StatusCode {

    int code();

    String description();

    HttpStatusCodeStatus status();

}
