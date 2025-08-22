package de.holube.request_sink.validation.http.status_code;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusCodeCsvReaderTest {

    @Test
    void testRead() {
        Map<Integer, HttpStatusCode> statusCodes = HttpStatusCodeCsvReader.read();
        assertFalse(statusCodes.isEmpty());
        assertFalse(statusCodes.containsKey(600));

        // first
        HttpStatusCode statusCode = statusCodes.get(100);
        assertNotNull(statusCode);
        assertEquals(100, statusCode.code());
        assertEquals("Continue", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());

        // any standard code
        statusCode = statusCodes.get(200);
        assertNotNull(statusCode);
        assertEquals(200, statusCode.code());
        assertEquals("OK", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());

        // any unassigned code
        statusCode = statusCodes.get(150);
        assertNotNull(statusCode);
        assertEquals(150, statusCode.code());
        assertEquals("Unassigned", statusCode.description());
        assertEquals(HttpStatusCodeStatus.UNASSIGNED, statusCode.status());

        // any unused code
        statusCode = statusCodes.get(306);
        assertNotNull(statusCode);
        assertEquals(306, statusCode.code());
        assertEquals("(Unused)", statusCode.description());
        assertEquals(HttpStatusCodeStatus.UNUSED, statusCode.status());

        // any out of range code
        statusCode = statusCodes.get(999);
        assertNull(statusCode);
    }

}
