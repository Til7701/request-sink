package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

/**
 * Utility class to access HTTP status codes.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class HttpStatusCodes {

    /**
     * Gets the HttpStatusCode for the given code.
     * If the code is not found, returns a StatusCode with description "Unknown Status Code" and status OUT_OF_RANGE.
     * Otherwise, returns the corresponding HttpStatusCode.
     *
     * @param code the HTTP status code to look up
     * @return the corresponding HttpStatusCode, or a default one if not found
     */
    public static HttpStatusCode get(int code) {
        if (code < 100 || code > 599) {
            return new StatusCode(code, "Out of Range Status Code", HttpStatusCodeStatus.OUT_OF_RANGE);
        }
        return HttpStatusCodeCsvReader.readOne(code);
    }

}
