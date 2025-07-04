package de.holube.request_sink.io;

public final class OutputBuilder {

    private final GroupBuilder metadata = new GroupBuilder();
    private final GroupBuilder headers = new GroupBuilder();

    private String body = "";
    private int id;

    public void addMetadataLine(String key, String value) {
        metadata.addLine(new LineBuilder(key, value));
    }

    public void addHeaderLine(String key, String value) {
        headers.addLine(new LineBuilder(key, value));
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String build() {
        final int metadataLength = metadata.getLength();
        final int headersLength = headers.getLength();
        final int maxLength = Math.max(metadataLength, headersLength);

        final StringBuilder sb = new StringBuilder();

        printSeparator(sb, "Request " + id + " Start", "=", maxLength);
        metadata.build(sb);
        printSeparator(sb, "Headers Start", "-", maxLength);
        headers.build(sb);
        printSeparator(sb, "Headers End", "-", maxLength);
        if (body.isEmpty())
            printSeparator(sb, "No body in request", "-", maxLength);
        else {
            printSeparator(sb, "Body Start", "-", maxLength);
            sb.append(body).append("\n");
            printSeparator(sb, "Body End", "-", maxLength);
        }
        printSeparator(sb, "Request " + id + " End", "=", maxLength);

        return sb.toString();
    }

    private void printSeparator(StringBuilder sb, String message, String sides, int length) {
        int sideLengths = (length - message.length() - 2) / 2;

        sb.append(sides.repeat(sideLengths))
                .append(" ").append(message).append(" ")
                .append(sides.repeat(sideLengths));
        if (sideLengths * 2 + 2 + message.length() < length)
            sb.append(sides);
        sb.append("\n");
    }

}
