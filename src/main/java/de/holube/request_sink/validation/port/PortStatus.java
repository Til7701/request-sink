package de.holube.request_sink.validation.port;

/**
 * Enum representing the status of a network port.
 */
public enum PortStatus {

    /**
     * The port is assigned to a specific service.
     */
    ASSIGNED,

    /**
     * The port is not assigned to any service.
     */
    UNASSIGNED,

    /**
     * The port is reserved and not available for general use.
     */
    RESERVED,

    /**
     * The port number is out of the valid range (0-65535).
     */
    OUT_OF_RANGE,

}
