package de.holube.request_sink.validation.port;

import lombok.NoArgsConstructor;

/**
 * Utility class to access port information.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Ports {

    /**
     * Gets the Port for the given port number.
     * If the code is out of range (less than 0 or greater than 65535), returns a Port with status OUT_OF_RANGE.
     * If the code is in the dynamic/private range (49152-65535), returns a Port with status UNASSIGNED.
     * Otherwise, returns the corresponding Port.
     *
     * @param number the port number to look for
     * @return the corresponding Port
     */
    public static Port get(int number) {
        if (number < 0 || number > 65535) {
            return new PortRecord(number, "", "Out of Range Port", PortStatus.OUT_OF_RANGE);
        } else if (number >= 49152) {
            return new PortRecord(number, "", "Dynamic or Private Ports", PortStatus.UNASSIGNED);
        }
        return PortCsvReader.readOne(number);
    }

}
