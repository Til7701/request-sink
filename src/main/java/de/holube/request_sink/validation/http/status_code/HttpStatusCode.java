package de.holube.request_sink.validation.http.status_code;

/**
 * Represents an HTTP status code with its numeric code, description, and status type.
 */
public sealed interface HttpStatusCode permits StatusCode {

    /**
     * The numeric HTTP status code.
     *
     * @return the status code as an integer
     */
    int code();

    /**
     * A brief description of the HTTP status code.
     *
     * @return the status code description
     */
    String description();

    /**
     * The status type of the HTTP status code (e.g., STANDARD, UNASSIGNED, UNUSED).
     *
     * @return the status type
     */
    HttpStatusCodeStatus status();

}
