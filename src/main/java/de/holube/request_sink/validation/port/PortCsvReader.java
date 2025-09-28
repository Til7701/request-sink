package de.holube.request_sink.validation.port;

import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Utility class to read port numbers and their descriptions from a CSV file.
 * <p>
 * The CSV file should be located in the same package as this class and named "service-names-port-numbers.csv".
 * The file can be obtained from <a href="https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xml">IANA</a>.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
final class PortCsvReader {

    /**
     * Reads the CSV file and returns the HttpStatusCode for the given code.
     * <p>
     * This method should only be used to read a single port, as it reads the file each time it is called.
     * If more than one port is needed, consider caching the results or reading the file once and storing the results in
     * a map.
     *
     * @param number the port number to look for
     * @return the Port object containing the port number and its description
     * @throws RuntimeException if the CSV file cannot be read or the port number is not found
     */
    static Port readOne(int number) {
        try (InputStream is = PortCsvReader.class.getResourceAsStream("service-names-port-numbers.csv")) {
            if (is == null) {
                throw new RuntimeException("Resource 'service-names-port-numbers.csv' not found");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                br.readLine(); // skip header NOSONAR
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank() && !line.startsWith(" ")) {
                        Optional<Port> port = parseFromLineWithCode(line, number);
                        if (port.isPresent()) {
                            return port.get();
                        }
                    }
                }
            }
            throw new RuntimeException("Port with number " + number + " not found!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ports", e);
        }
    }

    private static Optional<Port> parseFromLineWithCode(String csvLine, int number) {
        String[] parts = csvLine.split(",");
        if (parts.length < 4) {
            return Optional.empty();
        }

        if (lineContainsPort(parts[1], number)) {
            String serviceName = parts[0].trim();
            String description = parts[3].trim();
            PortStatus status = parseStatus(description);
            return Optional.of(new PortRecord(number, serviceName, description, status));
        }
        return Optional.empty();
    }

    private static boolean lineContainsPort(String codeStr, int number) {
        if (codeStr.contains("-")) {
            String[] rangeParts = codeStr.split("-");
            try {
                int start = Integer.parseInt(rangeParts[0].trim());
                int end = Integer.parseInt(rangeParts[1].trim());
                return number >= start && number <= end;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            try {
                return Integer.parseInt(codeStr.trim()) == number;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private static PortStatus parseStatus(String description) {
        if (description.equals("Reserved")) {
            return PortStatus.RESERVED;
        } else if (description.equals("Unassigned")) {
            return PortStatus.UNASSIGNED;
        } else {
            return PortStatus.ASSIGNED;
        }
    }

}
