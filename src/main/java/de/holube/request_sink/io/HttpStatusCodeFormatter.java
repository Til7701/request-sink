package de.holube.request_sink.io;

import de.holube.request_sink.validation.http.status_code.HttpStatusCode;
import lombok.NoArgsConstructor;
import picocli.CommandLine;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class HttpStatusCodeFormatter {

    public static String format(HttpStatusCode httpStatusCode) {
        int statusCode = httpStatusCode.code();
        String description = httpStatusCode.description();
        String style;
        style = switch (httpStatusCode.status()) {
            case STANDARD -> "green";
            case UNUSED -> "yellow";
            case UNASSIGNED -> "red";
            case OUT_OF_RANGE -> "red,bold";
        };
        return CommandLine.Help.Ansi.AUTO.string("@|" + style + " " + statusCode + " - " + description + "|@");
    }

}
