package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Utility class to read HTTP status codes from a CSV file and convert them into a map of HttpStatusCode objects.
 * <p>
 * The CSV file should be located in the same package as this class and named "http-status-codes-1.csv".
 * The file can be obtained from <a href="https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml">IANA</a>.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
final class HttpStatusCodeCsvReader {

    /**
     * Reads the CSV file and returns the HttpStatusCode for the given code.
     * <p>
     * This method should only be used to read a single code, as it reads the file each time it is called.
     * If more than one code is needed, consider caching the results or reading the file once and storing the results in
     * a map.
     *
     * @param code the HTTP status code to look up
     * @return the corresponding HttpStatusCode
     * @throws RuntimeException if the code is not found or if there is an error reading the file
     */
    static HttpStatusCode readOne(int code) {
        if (code < 100 || code > 599) throw new RuntimeException("Invalid HTTP status code " + code);
        try (InputStream is = HttpStatusCodes.class.getResourceAsStream("http-status-codes-1.csv")) {
            if (is == null) {
                throw new RuntimeException("Resource 'http_status_codes-1.csv' not found");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                br.readLine(); // skip header NOSONAR
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank()) {
                        Optional<HttpStatusCode> statusCode = parseFromLineWithCode(line, code);
                        if (statusCode.isPresent()) {
                            return statusCode.get();
                        }
                    }
                }
            }
            throw new RuntimeException("HTTP status code " + code + " not found!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load HTTP status codes", e);
        }
    }

    private static Optional<HttpStatusCode> parseFromLineWithCode(String csvLine, int code) {
        String[] parts = csvLine.split(",");

        if (lineContainsCode(parts[0], code)) {
            String description = parts[1].trim();
            HttpStatusCodeStatus status = parseStatus(parts[1].trim());
            return Optional.of(new StatusCode(code, description, status));
        }
        return Optional.empty();
    }

    private static boolean lineContainsCode(String codeStr, int code) {
        if (codeStr.contains("-")) {
            String[] rangeParts = codeStr.split("-");
            try {
                int start = Integer.parseInt(rangeParts[0].trim());
                int end = Integer.parseInt(rangeParts[1].trim());
                return code >= start && code <= end;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid status code range: " + codeStr, e);
            }
        } else {
            try {
                return Integer.parseInt(codeStr.trim()) == code;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid status code: " + codeStr, e);
            }
        }
    }

    private static HttpStatusCodeStatus parseStatus(String description) {
        if (description.equals("Unassigned")) {
            return HttpStatusCodeStatus.UNASSIGNED;
        } else if (description.equals("(Unused)")) {
            return HttpStatusCodeStatus.UNUSED;
        } else {
            return HttpStatusCodeStatus.STANDARD;
        }
    }

}
