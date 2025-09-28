package de.holube.request_sink.validation.port;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PortCsvReaderTest {

    @Test
    void testRead() {
        // first
        Port statusCode = PortCsvReader.readOne(0);
        assertEquals("", statusCode.serviceName());
        assertEquals(0, statusCode.number());
        assertEquals("Reserved", statusCode.description());
        assertEquals(PortStatus.RESERVED, statusCode.status());

        // ssh
        statusCode = PortCsvReader.readOne(22);
        assertEquals("ssh", statusCode.serviceName());
        assertEquals(22, statusCode.number());
        assertEquals("The Secure Shell (SSH) Protocol", statusCode.description());
        assertEquals(PortStatus.ASSIGNED, statusCode.status());

        // unassigned
        statusCode = PortCsvReader.readOne(60);
        assertEquals("", statusCode.serviceName());
        assertEquals(60, statusCode.number());
        assertEquals("Unassigned", statusCode.description());
        assertEquals(PortStatus.UNASSIGNED, statusCode.status());

        // last user port
        statusCode = PortCsvReader.readOne(49151);
        assertEquals("", statusCode.serviceName());
        assertEquals(49151, statusCode.number());
        assertEquals("Reserved", statusCode.description());
        assertEquals(PortStatus.RESERVED, statusCode.status());

        // out of range
        assertThrows(RuntimeException.class, () -> PortCsvReader.readOne(65536));
        assertThrows(RuntimeException.class, () -> PortCsvReader.readOne(-1));
    }

}
