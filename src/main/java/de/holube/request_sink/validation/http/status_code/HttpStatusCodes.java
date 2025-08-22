package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class HttpStatusCodes {

    private static Map<Integer, HttpStatusCode> statusCodes = null;

    public static HttpStatusCode get(int code) {
        if (statusCodes == null) {
            statusCodes = HttpStatusCodeCsvReader.read();
        }
        HttpStatusCode statusCode = statusCodes.get(code);
        if (statusCode == null) {
            return new StatusCode(code, "Unknown Status Code", HttpStatusCodeStatus.OUT_OF_RANGE);
        }
        return statusCode;
    }

}
