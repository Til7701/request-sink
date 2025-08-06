package de.holube.request_sink.cli.mixins;

import picocli.CommandLine;

/**
 * Mixin for providing a help option to the command line interface.
 * This mixin adds a `-h` or `--help` option that displays the help message.
 */
public final class HelpMixin {

    @SuppressWarnings("unused")
    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message."
    )
    private boolean helpRequested;

}
