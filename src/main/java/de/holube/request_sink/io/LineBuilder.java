package de.holube.request_sink.io;

record LineBuilder(
        String key,
        String value
) {

    void build(StringBuilder sb, int keyWidth) {
        sb.append(key).append(": ");
        sb.append(" ".repeat(keyWidth - key.length()));
        sb.append(value).append("\n");
    }

}
