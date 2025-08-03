package de.holube.request_sink.cli;

import de.holube.request_sink.cli.mixins.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
        name = "config",
        description = "Configures default options for the request sink server.",
        subcommands = {
                ConfigListCommand.class,
                ConfigSetCommand.class,
                ConfigUnsetCommand.class
        }
)
public final class ConfigCommand {

    @SuppressWarnings("unused")
    @CommandLine.Mixin
    private HelpMixin helpMixin;

}
