package de.holube.request_sink.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutputBuilderTest {

    @Test
    void testBuildOutput() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(1);
        outputBuilder.addMetadataLine("Method", "GET");
        outputBuilder.addMetadataLine("URL", "/api/test");
        outputBuilder.addHeaderLine("Content-Type", "application/json");
        outputBuilder.addHeaderLine("User-Agent", "TestAgent/1.0");
        outputBuilder.setBody("{\"key\":\"value\"}");

        String actualOutput = outputBuilder.build();

        String expectedOutput = """
                ====== Request 1 Start =======
                Method: GET
                URL:    /api/test
                ------- Headers Start --------
                Content-Type: application/json
                User-Agent:   TestAgent/1.0
                -------- Headers End ---------
                --------- Body Start ---------
                {"key":"value"}
                ---------- Body End ----------
                ======= Request 1 End ========
                """;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testBuildOutputWithoutBody() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(2);
        outputBuilder.addMetadataLine("Method", "POST");
        outputBuilder.addMetadataLine("URL", "/api/submit");
        outputBuilder.addHeaderLine("Content-Type", "application/x-www-form-urlencoded");
        outputBuilder.addHeaderLine("User-Agent", "TestAgent/2.0");
        outputBuilder.setBody("");

        String actualOutput = outputBuilder.build();

        String expectedOutput = """
                =============== Request 2 Start ===============
                Method: POST
                URL:    /api/submit
                ---------------- Headers Start ----------------
                Content-Type: application/x-www-form-urlencoded
                User-Agent:   TestAgent/2.0
                ----------------- Headers End -----------------
                ------------- No body in request --------------
                ================ Request 2 End ================
                """;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testBuildOutputWithEmptyMetadataAndHeaders() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(3);
        outputBuilder.setBody("No metadata or headers");
        String actualOutput = outputBuilder.build();
        String expectedOutput = """
                == Request 3 Start ==
                --- Headers Start ---
                ---- Headers End ----
                ---- Body Start -----
                No metadata or headers
                ----- Body End ------
                === Request 3 End ===
                """;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testBuildOutputWithOnlyMetadata() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(4);
        outputBuilder.addMetadataLine("Method", "PUT");
        outputBuilder.addMetadataLine("URL", "/api/update?id=123");
        outputBuilder.setBody("");

        String actualOutput = outputBuilder.build();

        String expectedOutput = """
                ==== Request 4 Start =====
                Method: PUT
                URL:    /api/update?id=123
                ----- Headers Start ------
                ------ Headers End -------
                --- No body in request ---
                ===== Request 4 End ======
                """;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testBuildOutputWithLongId() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(123456789);
        outputBuilder.addMetadataLine("Method", "DELETE");
        outputBuilder.addMetadataLine("URL", "/api/delete?id=456");
        outputBuilder.addHeaderLine("Content-Type", "text/plain");
        outputBuilder.setBody("This is a test body for a long ID.");

        String actualOutput = outputBuilder.build();

        String expectedOutput = """
                == Request 123456789 Start ==
                Method: DELETE
                URL:    /api/delete?id=456
                ------- Headers Start -------
                Content-Type: text/plain
                -------- Headers End --------
                -------- Body Start ---------
                This is a test body for a long ID.
                --------- Body End ----------
                === Request 123456789 End ===
                """;
        assertEquals(expectedOutput, actualOutput);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testAddMetadataLineWithNull() {
        OutputBuilder outputBuilder = new OutputBuilder();
        assertThrows(NullPointerException.class, () -> outputBuilder.addMetadataLine(null, "value"));
        assertThrows(NullPointerException.class, () -> outputBuilder.addMetadataLine("key", null));
        assertThrows(NullPointerException.class, () -> outputBuilder.addMetadataLine(null, null));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testAddHeaderLineWithNull() {
        OutputBuilder outputBuilder = new OutputBuilder();
        assertThrows(NullPointerException.class, () -> outputBuilder.addHeaderLine(null, "value"));
        assertThrows(NullPointerException.class, () -> outputBuilder.addHeaderLine("key", null));
        assertThrows(NullPointerException.class, () -> outputBuilder.addHeaderLine(null, null));
    }

    @Test
    void testSetBodyWithNull() {
        OutputBuilder outputBuilder = new OutputBuilder();
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> outputBuilder.setBody(null));
    }

    @Test
    void testWithMaxWidth() {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.setId(42);
        outputBuilder.addMetadataLine("URL", "/api/patch?id=78934532946509236592456456346752347986792348769074259345602906592396");
        outputBuilder.addHeaderLine("Content-Type", "application/json");
        outputBuilder.addHeaderLine("User-Agent", "TestAgent/3.0");
        outputBuilder.setBody("{\"key\":\"value\"}");

        String actualOutput = outputBuilder.build();

        // Default width by picocli is 80 characters.
        String expectedOutput1 = """
                =============================== Request 42 Start ===============================
                URL: /api/patch?id=78934532946509236592456456346752347986792348769074259345602906592396
                -------------------------------- Headers Start ---------------------------------
                Content-Type: application/json
                User-Agent:   TestAgent/3.0
                --------------------------------- Headers End ----------------------------------
                ---------------------------------- Body Start ----------------------------------
                {"key":"value"}
                ----------------------------------- Body End -----------------------------------
                ================================ Request 42 End ================================
                """;
        String expectedOutput2 = """
                ================================== Request 42 Start ===================================
                URL: /api/patch?id=78934532946509236592456456346752347986792348769074259345602906592396
                ------------------------------------ Headers Start ------------------------------------
                Content-Type: application/json
                User-Agent:   TestAgent/3.0
                ------------------------------------- Headers End -------------------------------------
                ------------------------------------- Body Start --------------------------------------
                {"key":"value"}
                -------------------------------------- Body End ---------------------------------------
                =================================== Request 42 End ====================================
                """;
        // Depending on the environment (OS, terminal, IDE) we detect different default widths.
        if (!actualOutput.equals(expectedOutput1) && !actualOutput.equals(expectedOutput2)) {
            throw new AssertionError("Output did not match expected output for width.\n" +
                    "Expected:\n" + expectedOutput1 + "\n" +
                    "Expected:\n" + expectedOutput2 + "\n" +
                    "Actual:\n" + actualOutput);
        }
    }

}
