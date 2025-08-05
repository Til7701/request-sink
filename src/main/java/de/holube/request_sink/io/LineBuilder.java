package de.holube.request_sink.io;

/**
 * This class represents a single line in a formatted output.
 * It contains a key and a value, and provides a method to build the output line
 * with proper formatting.
 * The key is left-aligned, and the value follows it with a fixed width.
 * The width of the key can be specified to ensure consistent formatting
 * across multiple lines.
 *
 * @param key   the key for the line, which will be left-aligned
 * @param value the value for the line, which will follow the key
 */
public record LineBuilder(
        String key,
        String value
) {

    /**
     * Constructs a LineBuilder with the specified key and value.
     *
     * @param key   the key for the line, must not be null
     * @param value the value for the line, must not be null
     */
    public LineBuilder {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null.");
        }
    }

    /**
     * Builds the formatted line and appends it to the provided StringBuilder.
     * The key is followed by a colon and a space, then padded with spaces
     * to match the specified key width, and finally the value is appended.
     * <p>
     * The key width must be greater than or equal to the length of the key.
     *
     * @param sb       the StringBuilder to append the formatted line to
     * @param keyWidth the width to which the key should be padded
     */
    void build(StringBuilder sb, int keyWidth) {
        if (keyWidth < key.length()) // should never happen
            throw new IllegalArgumentException("Key width is too small for the given key.");

        sb.append(key).append(": ");
        sb.append(" ".repeat(keyWidth - key.length()));
        sb.append(value).append("\n");
    }

}
