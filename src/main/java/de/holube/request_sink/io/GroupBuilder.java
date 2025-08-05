package de.holube.request_sink.io;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for a group of lines, which can be used to format multiple key-value pairs
 * into a structured output.
 * <p>
 * This class collects multiple {@link LineBuilder} instances and calculates the maximum
 * lengths of keys and values to ensure proper alignment when building the final output.
 */
public final class GroupBuilder {

    private final List<LineBuilder> lineBuilders = new ArrayList<>();

    private int maxKeyLength = 0;
    private int maxValueLength = 0;

    /**
     * Adds a {@link LineBuilder} to the group.
     *
     * @param lineBuilder the {@link LineBuilder} to add
     */
    public void addLine(LineBuilder lineBuilder) {
        lineBuilders.add(lineBuilder);

        final int keyLength = lineBuilder.key().length();
        if (maxKeyLength < keyLength)
            maxKeyLength = keyLength;

        final int valueLength = lineBuilder.value().length();
        if (maxValueLength < valueLength)
            maxValueLength = valueLength;
    }

    /**
     * Returns the maximum length of lines in the group.
     * This is calculated as the sum of the maximum key length,
     * the length of the colon and space (2 characters),
     * and the maximum value length.
     *
     * @return the maximum length of lines
     */
    int getLength() {
        return maxKeyLength + 2 + maxValueLength;
    }

    /**
     * Builds the final output by formatting all lines in the group.
     *
     * @param sb the {@link StringBuilder} to append the formatted output to
     */
    public void build(StringBuilder sb) {
        lineBuilders.forEach(lb -> lb.build(sb, maxKeyLength));
    }

}
