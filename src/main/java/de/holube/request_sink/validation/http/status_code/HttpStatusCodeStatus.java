package de.holube.request_sink.validation.http.status_code;

/**
 * Enumeration representing the status types of HTTP status codes.
 */
public enum HttpStatusCodeStatus {

    /**
     * Indicates a standard, officially recognized HTTP status code as defined by the IANA.
     */
    STANDARD,

    /**
     * Indicates an HTTP status code that is not currently used by the IANA and is reserved for potential future use.
     */
    UNUSED,

    /**
     * Indicates an HTTP status code that is not assigned by the IANA and has no official meaning.
     */
    UNASSIGNED,

    /**
     * Indicates an HTTP status code that falls outside the valid range of 100 to 599.
     */
    OUT_OF_RANGE,

}
