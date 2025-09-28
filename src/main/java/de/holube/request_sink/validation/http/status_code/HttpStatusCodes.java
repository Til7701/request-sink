package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility class to access HTTP status codes.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class HttpStatusCodes {

    private static final Supplier<Map<Integer, HttpStatusCode>> statusCodes = StableValue.supplier(HttpStatusCodeCsvReader::read);
    private static final Supplier<HttpStatusCode> unknownStatusCode = StableValue.supplier(() ->
            new StatusCode(0, "Unknown Status Code", HttpStatusCodeStatus.OUT_OF_RANGE));

    /**
     * Gets the HttpStatusCode for the given code.
     * If the code is not found, returns a StatusCode with description "Unknown Status Code" and status OUT_OF_RANGE.
     * Otherwise, returns the corresponding HttpStatusCode.
     *
     * @param code the HTTP status code to look up
     * @return the corresponding HttpStatusCode, or a default one if not found
     */
    public static HttpStatusCode get(int code) {
        HttpStatusCode statusCode = statusCodes.get().get(code);
        if (statusCode == null) {
            return unknownStatusCode.get();
        }
        return statusCode;
    }

}
