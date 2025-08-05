package de.holube.request_sink.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LineBuilderTest {

    @Test
    void testConstructorWithNullKey() {
        assertThrows(NullPointerException.class, () -> new LineBuilder(null, "value"));
    }

    @Test
    void testConstructorWithNullValue() {
        assertThrows(NullPointerException.class, () -> new LineBuilder("key", null));
    }

    @Test
    void testConstructorWithBothNull() {
        assertThrows(NullPointerException.class, () -> new LineBuilder(null, null));
    }

    @Test
    void testBuild() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("key", "value");

        lineBuilder.build(sb, 10);

        assertEquals("key:        value\n", sb.toString());
    }

    @Test
    void testBuildWithTooLongKey() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("longerKey", "value");

        assertThrows(IllegalArgumentException.class, () -> lineBuilder.build(sb, 5));
    }

    @Test
    void testBuildWithExactKeyLength() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("key", "value");

        lineBuilder.build(sb, 3);

        assertEquals("key: value\n", sb.toString());
    }

    @Test
    void testBuildWithZeroKeyWidth() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("key", "value");

        assertThrows(IllegalArgumentException.class, () -> lineBuilder.build(sb, 0));
    }

    @Test
    void testBuildWithNegativeKeyWidth() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("key", "value");

        assertThrows(IllegalArgumentException.class, () -> lineBuilder.build(sb, -1));
    }

    @Test
    void testBuildWithEmptyKey() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("", "value");

        lineBuilder.build(sb, 2);

        assertEquals(":   value\n", sb.toString());
    }

    @Test
    void testBuildWithEmptyValue() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("key", "");

        lineBuilder.build(sb, 5);

        assertEquals("key:   \n", sb.toString());
    }

    @Test
    void testBuildWithBothEmpty() {
        StringBuilder sb = new StringBuilder();
        LineBuilder lineBuilder = new LineBuilder("", "");

        lineBuilder.build(sb, 2);

        assertEquals(":   \n", sb.toString());
    }

}
