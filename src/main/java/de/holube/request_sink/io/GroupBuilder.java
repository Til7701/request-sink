package de.holube.request_sink.io;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for a group of lines, which can be used to format multiple key-value pairs
 * into a structured output.
 * <p>
 * This class collects multiple {@link LineBuilder} instances and calculates the maximum
 * widths of keys and values to ensure proper alignment when building the final output.
 */
public final class GroupBuilder {

    private final List<LineBuilder> lineBuilders = new ArrayList<>();

    private int maxKeyWidth = 0;
    private int maxValueWidth = 0;

    /**
     * Adds a {@link LineBuilder} to the group.
     *
     * @param lineBuilder the {@link LineBuilder} to add
     */
    public void addLine(@NonNull LineBuilder lineBuilder) {
        lineBuilders.add(lineBuilder);

        final int keyLength = lineBuilder.key().length();
        if (maxKeyWidth < keyLength)
            maxKeyWidth = keyLength;

        final int valueLength = lineBuilder.value().length();
        if (maxValueWidth < valueLength)
            maxValueWidth = valueLength;
    }

    /**
     * Returns the maximum width of lines in the group.
     * This is calculated as the sum of the maximum key width,
     * the width of the colon and space (2 characters),
     * and the maximum value width.
     *
     * @return the maximum width of lines
     */
    int getWidth() {
        return maxKeyWidth + 2 + maxValueWidth;
    }

    /**
     * Builds the final output by formatting all lines in the group.
     *
     * @param sb the {@link StringBuilder} to append the formatted output to
     */
    public void build(@NonNull StringBuilder sb) {
        lineBuilders.forEach(lb -> lb.build(sb, maxKeyWidth));
    }

}
