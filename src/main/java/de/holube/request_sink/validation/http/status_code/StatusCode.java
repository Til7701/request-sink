package de.holube.request_sink.validation.http.status_code;

/**
 * Implementation of the HttpStatusCode interface.
 *
 * @param code        the numeric HTTP status code
 * @param description a brief description of the HTTP status code
 * @param status      the status type of the HTTP status code (e.g., STANDARD, UNASSIGNED, UNUSED)
 */
record StatusCode(
        int code,
        String description,
        HttpStatusCodeStatus status
) implements HttpStatusCode {
}
