package de.holube.request_sink.validation.http.status_code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpStatusCodeCsvReaderTest {

    @Test
    void testRead() {
        // first
        HttpStatusCode statusCode = HttpStatusCodeCsvReader.readOne(100);
        assertEquals(100, statusCode.code());
        assertEquals("Continue", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());

        // any standard code
        statusCode = HttpStatusCodeCsvReader.readOne(200);
        assertEquals(200, statusCode.code());
        assertEquals("OK", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());

        // any unassigned code
        statusCode = HttpStatusCodeCsvReader.readOne(150);
        assertEquals(150, statusCode.code());
        assertEquals("Unassigned", statusCode.description());
        assertEquals(HttpStatusCodeStatus.UNASSIGNED, statusCode.status());

        // any unused code
        statusCode = HttpStatusCodeCsvReader.readOne(306);
        assertEquals(306, statusCode.code());
        assertEquals("(Unused)", statusCode.description());
        assertEquals(HttpStatusCodeStatus.UNUSED, statusCode.status());

        // last standard code
        statusCode = HttpStatusCodeCsvReader.readOne(511);
        assertEquals(511, statusCode.code());
        assertEquals("Network Authentication Required", statusCode.description());
        assertEquals(HttpStatusCodeStatus.STANDARD, statusCode.status());

        // last unassigned code
        statusCode = HttpStatusCodeCsvReader.readOne(599);
        assertEquals(599, statusCode.code());
        assertEquals("Unassigned", statusCode.description());
        assertEquals(HttpStatusCodeStatus.UNASSIGNED, statusCode.status());

        // any out of range code
        assertThrows(RuntimeException.class, () -> HttpStatusCodeCsvReader.readOne(999));
    }

}
