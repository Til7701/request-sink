package de.holube.request_sink.cli;

import de.holube.request_sink.cli.mixins.HelpMixin;
import de.holube.request_sink.config.Pref;
import de.holube.request_sink.config.Prefs;
import de.holube.request_sink.io.GroupBuilder;
import de.holube.request_sink.io.LineBuilder;
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
                    pref.getKey(),
                    Prefs.get(pref)
            );
            groupBuilder.addLine(lineBuilder);
        }
        StringBuilder sb = new StringBuilder();
        groupBuilder.build(sb);
        IO.print(sb.toString());
    }

}
