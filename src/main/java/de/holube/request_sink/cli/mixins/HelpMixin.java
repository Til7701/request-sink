package de.holube.request_sink.cli.mixins;

import picocli.CommandLine;

@CommandLine.Command
public final class HelpMixin {

    @SuppressWarnings("unused")
    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message."
    )
    private boolean helpRequested;

}