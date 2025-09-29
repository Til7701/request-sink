package de.holube.request_sink.io;

import lombok.NonNull;
import lombok.Setter;
import picocli.CommandLine;

public final class OutputBuilder {

    @Setter
    private int id;

    private final GroupBuilder metadata = new GroupBuilder();
    private final GroupBuilder headers = new GroupBuilder();

    @Setter
    @NonNull
    private String body = "";

    public void addMetadataLine(@NonNull String key, @NonNull String value) {
        metadata.addLine(new LineBuilder(key, value));
    }

    public void addHeaderLine(@NonNull String key, @NonNull String value) {
        headers.addLine(new LineBuilder(key, value));
    }

    public String build() {
        CommandLine.tracer().debug("Building output for id: %s", id);
        final int maxWidth = getWidth();
        CommandLine.tracer().debug("Max Width: %s", maxWidth);

        final StringBuilder sb = new StringBuilder();

        buildSeparator(sb, "Request " + id + " Start", "=", maxWidth);
        metadata.build(sb);
        buildSeparator(sb, "Headers Start", "-", maxWidth);
        headers.build(sb);
        buildSeparator(sb, "Headers End", "-", maxWidth);
        if (body.isEmpty())
            buildSeparator(sb, "No body in request", "-", maxWidth);
        else {
            buildSeparator(sb, "Body Start", "-", maxWidth);
            sb.append(body).append("\n");
            buildSeparator(sb, "Body End", "-", maxWidth);
        }
        buildSeparator(sb, "Request " + id + " End", "=", maxWidth);

        return sb.toString();
    }

    private int getWidth() {
        final int metadataLength = metadata.getWidth();
        final int headersLength = headers.getWidth();
        final int minWidth = String.valueOf(id).length() + 20;
        final int maxWidth = Math.max(Math.max(metadataLength, headersLength), minWidth);
        final int terminalWidth = Terminal.getWidth();
        CommandLine.tracer().debug("Detected terminal width: %d", terminalWidth);
        return Math.min(maxWidth, terminalWidth);
    }

    private void buildSeparator(StringBuilder sb, String message, String sides, int maxWidth) {
        int sideWidths = (maxWidth - message.length() - 2) / 2;

        sb.append(sides.repeat(sideWidths))
                .append(" ").append(message).append(" ")
                .append(sides.repeat(sideWidths));
        if (sideWidths * 2 + 2 + message.length() < maxWidth)
            sb.append(sides);
        sb.append("\n");
    }

}
