package de.holube.request_sink.validation.http.status_code;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
final class HttpStatusCodeCsvReader {

    static Map<Integer, HttpStatusCode> read() {
        try (InputStream is = HttpStatusCodes.class.getResourceAsStream("http-status-codes-1.csv")) {
            if (is == null) {
                throw new RuntimeException("Resource 'http_status_codes-1.csv' not found");
            }
            byte[] bytes = is.readAllBytes();
            String csvContent = new String(bytes);
            String[] csvLines = csvContent.split("\n");
            Map<Integer, HttpStatusCode> statusCodes = new java.util.HashMap<>();
            for (int i = 1; i < csvLines.length; i++) {
                String line = csvLines[i];
                if (!line.isBlank()) {
                    List<HttpStatusCode> statusCode = fromCsvLine(line);
                    statusCode.forEach(sc -> {
                        var previous = statusCodes.put(sc.code(), sc);
                        if (previous != null) {
                            throw new IllegalStateException("Duplicate status code found: " + sc.code());
                        }
                    });
                }
            }
            return statusCodes;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load HTTP status codes", e);
        }
    }

    private static List<HttpStatusCode> fromCsvLine(String csvLine) {
        String[] parts = csvLine.split(",");

        List<Integer> codes = parseCode(parts[0]);
        String description = parts[1].trim();
        HttpStatusCodeStatus status = parseStatus(parts[1].trim());

        return codes.stream()
                .<HttpStatusCode>map(code -> new StatusCode(code, description, status))
                .toList();
    }

    private static List<Integer> parseCode(String codeStr) {
        if (codeStr.contains("-")) {
            String[] rangeParts = codeStr.split("-");
            try {
                int start = Integer.parseInt(rangeParts[0].trim());
                int end = Integer.parseInt(rangeParts[1].trim());
                return IntStream.rangeClosed(start, end)
                        .boxed()
                        .toList();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid status code range: " + codeStr, e);
            }
        } else {
            try {
                return List.of(Integer.parseInt(codeStr.trim()));
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
