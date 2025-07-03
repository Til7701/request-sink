package de.holube.request_sink.io;

import java.util.ArrayList;
import java.util.List;

final class GroupBuilder {

    private final List<LineBuilder> lineBuilders = new ArrayList<>();
    private String groupStart = "";
    private String groupEnd = "";

    private int maxKeyLength = 0;
    private int maxValueLength = 0;

    void addLine(LineBuilder lineBuilder) {
        lineBuilders.add(lineBuilder);

        final int keyLength = lineBuilder.key().length();
        if (maxKeyLength < keyLength)
            maxKeyLength = keyLength;

        final int valueLength = lineBuilder.value().length();
        if (maxValueLength < valueLength)
            maxValueLength = valueLength;
    }

    public void setGroupStart(String groupStart) {
        this.groupStart = groupStart;
    }

    public void setGroupEnd(String groupEnd) {
        this.groupEnd = groupEnd;
    }

    int getLength() {
        return maxKeyLength + 2 + maxValueLength;
    }

    void build(StringBuilder sb) {
        lineBuilders.forEach(lb -> lb.build(sb, maxKeyLength));
    }

}
