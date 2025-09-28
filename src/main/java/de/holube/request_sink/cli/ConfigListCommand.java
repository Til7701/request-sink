package de.holube.request_sink.cli;

import de.holube.request_sink.cli.mixins.HelpMixin;
import de.holube.request_sink.config.Pref;
import de.holube.request_sink.config.Prefs;
import de.holube.request_sink.io.GroupBuilder;
import de.holube.request_sink.io.HttpStatusCodeFormatter;
import de.holube.request_sink.io.LineBuilder;
import de.holube.request_sink.io.PortFormatter;
import de.holube.request_sink.validation.http.status_code.HttpStatusCodes;
import de.holube.request_sink.validation.port.Ports;
import picocli.CommandLine;

@CommandLine.Command(
        name = "list",
        description = "Lists all available configuration options and their values."
)
public final class ConfigListCommand implements Runnable {

    @SuppressWarnings("unused")
    @CommandLine.Mixin
    private HelpMixin helpMixin;

    @Override
    public void run() {
        GroupBuilder groupBuilder = new GroupBuilder();
        for (Pref pref : Pref.values()) {
            LineBuilder lineBuilder = new LineBuilder(
                    formatKey(pref.getKey()),
                    formatValue(pref, Prefs.get(pref))
            );
            groupBuilder.addLine(lineBuilder);
        }
        StringBuilder sb = new StringBuilder();
        groupBuilder.build(sb);
        IO.print(sb.toString());
    }

    private String formatKey(String key) {
        return CommandLine.Help.Ansi.AUTO.string("@|bold,underline " + key + "|@");
    }

    private String formatValue(Pref pref, String value) {
        return switch (pref) {
            case PORT -> PortFormatter.format(Ports.get(Integer.parseInt(value)));
            case STATUS_CODE -> HttpStatusCodeFormatter.format(HttpStatusCodes.get(Integer.parseInt(value)));
        };
    }

}
