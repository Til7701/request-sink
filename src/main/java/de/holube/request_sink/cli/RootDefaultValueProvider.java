package de.holube.request_sink.cli;

import de.holube.request_sink.config.Pref;
import de.holube.request_sink.config.Prefs;
import picocli.CommandLine;

public class RootDefaultValueProvider implements CommandLine.IDefaultValueProvider {

    @Override
    public String defaultValue(CommandLine.Model.ArgSpec argSpec) {
        if (argSpec instanceof CommandLine.Model.OptionSpec optionSpec) {
            if (optionSpec.longestName().equals("--port")) {
                return Prefs.get(Pref.PORT);
            } else if (optionSpec.longestName().equals("--status-code")) {
                return Prefs.get(Pref.STATUS_CODE);
            }
        }
        return null;
    }

}
