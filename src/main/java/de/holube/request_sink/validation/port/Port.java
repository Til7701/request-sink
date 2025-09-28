package de.holube.request_sink.validation.port;

/**
 * Represents a network port with its number, service name, description, and status.
 */
public sealed interface Port permits PortRecord {

    /**
     * Gets the port number.
     *
     * @return the port number
     */
    int number();

    /**
     * Gets the service name associated with the port.
     *
     * @return the service name
     */
    String serviceName();

    /**
     * Gets the description of the port.
     *
     * @return the port description
     */
    String description();

    /**
     * Gets the status of the port.
     *
     * @return the port status
     */
    PortStatus status();

}
