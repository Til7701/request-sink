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
        final int length = getLength();

        final StringBuilder sb = new StringBuilder();

        buildSeparator(sb, "Request " + id + " Start", "=", length);
        metadata.build(sb);
        buildSeparator(sb, "Headers Start", "-", length);
        headers.build(sb);
        buildSeparator(sb, "Headers End", "-", length);
        if (body.isEmpty())
            buildSeparator(sb, "No body in request", "-", length);
        else {
            buildSeparator(sb, "Body Start", "-", length);
            sb.append(body).append("\n");
            buildSeparator(sb, "Body End", "-", length);
        }
        buildSeparator(sb, "Request " + id + " End", "=", length);

        return sb.toString();
    }

    private int getLength() {
        final int metadataLength = metadata.getLength();
        final int headersLength = headers.getLength();
        final int minLength = String.valueOf(id).length() + 20;
        final int maxLength = Math.max(Math.max(metadataLength, headersLength), minLength);
        CommandLine.Model.UsageMessageSpec usageMessageSpec = new CommandLine.Model.UsageMessageSpec();
        usageMessageSpec.autoWidth(true);
        final int terminalWidth = usageMessageSpec.width();
        return Math.min(maxLength, terminalWidth);
    }

    private void buildSeparator(StringBuilder sb, String message, String sides, int length) {
        int sideLengths = (length - message.length() - 2) / 2;

        sb.append(sides.repeat(sideLengths))
                .append(" ").append(message).append(" ")
                .append(sides.repeat(sideLengths));
        if (sideLengths * 2 + 2 + message.length() < length)
            sb.append(sides);
        sb.append("\n");
    }

}
