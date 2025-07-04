package de.holube.request_sink.io;

import java.util.ArrayList;
import java.util.List;

final class GroupBuilder {

    private final List<LineBuilder> lineBuilders = new ArrayList<>();

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

    int getLength() {
        return maxKeyLength + 2 + maxValueLength;
    }

    void build(StringBuilder sb) {
        lineBuilders.forEach(lb -> lb.build(sb, maxKeyLength));
    }

}
