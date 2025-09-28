package de.holube.request_sink.cli.mixins;

import picocli.CommandLine;

/**
 * Mixin class to add a debug option to CLI commands.
 * When the --debug flag is provided, debug output will be enabled.
 */
public final class DebugMixin {

    @CommandLine.Option(
            names = {"--debug"},
            description = "Enable debug output"
    )
    public void setDebug(boolean debug) {
        if (debug)
            CommandLine.tracer().setLevel(CommandLine.TraceLevel.DEBUG);
    }

}
