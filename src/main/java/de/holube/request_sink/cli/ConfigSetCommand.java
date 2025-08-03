package de.holube.request_sink.cli;

import de.holube.request_sink.cli.completion.ConfigCompletionCandidates;
import de.holube.request_sink.cli.mixins.HelpMixin;
import de.holube.request_sink.config.Pref;
import de.holube.request_sink.config.Prefs;
import picocli.CommandLine;

import java.util.Optional;

@CommandLine.Command(
        name = "set",
        description = "Sets a configuration option."
)
public final class ConfigSetCommand implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @SuppressWarnings("unused")
    @CommandLine.Mixin
    HelpMixin helpMixin;

    @SuppressWarnings("unused")
    @CommandLine.Parameters(
            index = "0",
            description = "The configuration option to set.",
            completionCandidates = ConfigCompletionCandidates.class
    )
    private String key;

    @SuppressWarnings("unused")
    @CommandLine.Parameters(
            index = "1",
            description = "The value to set the configuration option to."
    )
    private String value;

    @Override
    public void run() {
        Optional<Pref> pref = Pref.fromKey(key);
        if (pref.isEmpty()) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Unknown configuration option: '" + key + "'. Use 'config list' to see available options."
            );
        }

        if (value == null || value.isBlank()) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Value for configuration option '" + key + "' must not be empty."
            );
        }

        Prefs.put(pref.get(), value);
    }

}
