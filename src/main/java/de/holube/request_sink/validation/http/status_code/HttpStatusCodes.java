package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class HttpStatusCodes {

    private static final Supplier<Map<Integer, HttpStatusCode>> statusCodes = StableValue.supplier(HttpStatusCodeCsvReader::read);

    public static HttpStatusCode get(int code) {
        HttpStatusCode statusCode = statusCodes.get().get(code);
        if (statusCode == null) {
            return new StatusCode(code, "Unknown Status Code", HttpStatusCodeStatus.OUT_OF_RANGE);
        }
        return statusCode;
    }

}
