package de.holube.request_sink.cli;

import de.holube.request_sink.cli.completion.ConfigCompletionCandidates;
import de.holube.request_sink.cli.mixins.HelpMixin;
import de.holube.request_sink.config.Pref;
import de.holube.request_sink.config.Prefs;
import picocli.CommandLine;

import java.util.Optional;

@CommandLine.Command(
        name = "unset",
        mixinStandardHelpOptions = true,
        description = "Unsets a configuration option."
)
public final class ConfigUnsetCommand implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @SuppressWarnings("unused")
    @CommandLine.Mixin
    HelpMixin helpMixin;

    @SuppressWarnings("unused")
    @CommandLine.Parameters(
            index = "0",
            description = "The configuration option to unset.",
            completionCandidates = ConfigCompletionCandidates.class
    )
    private String key;

    @Override
    public void run() {
        Optional<Pref> pref = Pref.fromKey(key);
        if (pref.isEmpty()) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Unknown configuration option: '" + key + "'. Use 'config list' to see available options."
            );
        }

        Prefs.unset(pref.get());
    }
}
