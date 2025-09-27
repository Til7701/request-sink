package de.holube.request_sink.validation.http.status_code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpStatusCodesTest {

    @Test
    void testGetKnownStatusCode() {
        HttpStatusCode statusCode = HttpStatusCodes.get(200);
        assertEquals(200, statusCode.code());
        assertEquals("OK", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());
    }

    @Test
    void testGetUnknownStatusCode() {
        HttpStatusCode statusCode = HttpStatusCodes.get(999);
        assertEquals(999, statusCode.code());
        assertEquals("Unknown Status Code", statusCode.description());
        assertEquals(HttpStatusCodeStatus.OUT_OF_RANGE, statusCode.status());
    }

}
