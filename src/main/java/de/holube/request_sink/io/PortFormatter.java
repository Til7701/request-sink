package de.holube.request_sink.io;

import de.holube.request_sink.validation.port.Port;
import lombok.NoArgsConstructor;
import picocli.CommandLine;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PortFormatter {

    public static String format(Port port) {
        int number = port.number();
        String description = port.description();
        String style;
        style = switch (port.status()) {
            case ASSIGNED -> "green";
            case UNASSIGNED -> "yellow";
            case RESERVED -> "red";
            case OUT_OF_RANGE -> "red,bold";
        };
        return CommandLine.Help.Ansi.AUTO.string("@|" + style + " " + number + " - " + description + "|@");
    }

}
