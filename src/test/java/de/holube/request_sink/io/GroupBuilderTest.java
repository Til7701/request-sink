package de.holube.request_sink.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GroupBuilderTest {

    @Test
    void testAddLine() {
        GroupBuilder groupBuilder = new GroupBuilder();
        LineBuilder lineBuilder1 = new LineBuilder("k1", "v1");
        LineBuilder lineBuilder2 = new LineBuilder("key2", "value2");

        groupBuilder.addLine(lineBuilder1);
        groupBuilder.addLine(lineBuilder2);

        assertEquals("key2: value2".length(), groupBuilder.getLength());
    }

    @Test
    void testAddLineWithNull() {
        GroupBuilder groupBuilder = new GroupBuilder();

        assertThrows(NullPointerException.class, () -> groupBuilder.addLine(null));
    }

    @Test
    void testBuild() {
        GroupBuilder groupBuilder = new GroupBuilder();
        LineBuilder lineBuilder1 = new LineBuilder("k1", "v1");
        LineBuilder lineBuilder2 = new LineBuilder("key2", "value2");
        groupBuilder.addLine(lineBuilder1);
        groupBuilder.addLine(lineBuilder2);

        StringBuilder sb = new StringBuilder();
        groupBuilder.build(sb);

        String expectedOutput = """
                k1:   v1
                key2: value2
                """;
        assertEquals(expectedOutput, sb.toString());
    }

    @Test
    void testGetLengthWithNoLines() {
        GroupBuilder groupBuilder = new GroupBuilder();
        assertEquals(2, groupBuilder.getLength());
    }

    @Test
    void testGetLengthWithSingleLine() {
        GroupBuilder groupBuilder = new GroupBuilder();
        LineBuilder lineBuilder = new LineBuilder("singleKey", "singleValue");

        groupBuilder.addLine(lineBuilder);

        assertEquals("singleKey: singleValue".length(), groupBuilder.getLength());
    }

    @Test
    void testGetLengthWithMultipleLines() {
        GroupBuilder groupBuilder = new GroupBuilder();
        LineBuilder lineBuilder1 = new LineBuilder("short", "value");
        LineBuilder lineBuilder2 = new LineBuilder("longerKey", "anotherValue");

        groupBuilder.addLine(lineBuilder1);
        groupBuilder.addLine(lineBuilder2);

        assertEquals("longerKey: anotherValue".length(), groupBuilder.getLength());
    }

    @Test
    void testGetLengthWithEmptyLine() {
        GroupBuilder groupBuilder = new GroupBuilder();
        LineBuilder lineBuilder1 = new LineBuilder("", "");

        groupBuilder.addLine(lineBuilder1);

        assertEquals(2, groupBuilder.getLength());
    }

}
