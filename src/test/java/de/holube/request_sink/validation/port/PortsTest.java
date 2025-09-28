package de.holube.request_sink.validation.port;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortsTest {

    @Test
    void testGetKnownPort() {
        Port port = Ports.get(22);
        assertEquals(22, port.number());
        assertEquals("ssh", port.serviceName());
        assertEquals("The Secure Shell (SSH) Protocol", port.description());
        assertEquals(PortStatus.ASSIGNED, port.status());
    }

    @Test
    void testGetUnassignedPort() {
        Port port = Ports.get(60);
        assertEquals(60, port.number());
        assertEquals("", port.serviceName());
        assertEquals("Unassigned", port.description());
        assertEquals(PortStatus.UNASSIGNED, port.status());
    }

    @Test
    void testGetReservedPort() {
        Port port = Ports.get(0);
        assertEquals(0, port.number());
        assertEquals("", port.serviceName());
        assertEquals("Reserved", port.description());
        assertEquals(PortStatus.RESERVED, port.status());
    }

    @Test
    void testGetDynamicPort() {
        Port port = Ports.get(50000);
        assertEquals(50000, port.number());
        assertEquals("", port.serviceName());
        assertEquals("Dynamic or Private Ports", port.description());
        assertEquals(PortStatus.UNASSIGNED, port.status());
    }

    @Test
    void testGetOutOfRangePort() {
        Port port = Ports.get(70000);
        assertEquals(70000, port.number());
        assertEquals("", port.serviceName());
        assertEquals("Out of Range Port", port.description());
        assertEquals(PortStatus.OUT_OF_RANGE, port.status());

        port = Ports.get(-1);
        assertEquals(-1, port.number());
        assertEquals("", port.serviceName());
        assertEquals("Out of Range Port", port.description());
        assertEquals(PortStatus.OUT_OF_RANGE, port.status());
    }

}
