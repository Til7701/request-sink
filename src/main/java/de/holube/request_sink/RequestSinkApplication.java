package de.holube.request_sink;

import de.holube.request_sink.cli.RootCommand;
import picocli.CommandLine;

public final class RequestSinkApplication {

    public static void main(String[] args) {
        CommandLine.Help.ColorScheme colorScheme = new CommandLine.Help.ColorScheme.Builder()
                .commands(CommandLine.Help.Ansi.Style.bold, CommandLine.Help.Ansi.Style.underline)
                .options(CommandLine.Help.Ansi.Style.fg_magenta)
                .parameters(CommandLine.Help.Ansi.Style.fg_magenta)
                .optionParams(CommandLine.Help.Ansi.Style.italic)
                .errors(CommandLine.Help.Ansi.Style.fg_red, CommandLine.Help.Ansi.Style.bold)
                .stackTraces(CommandLine.Help.Ansi.Style.italic)
                .applySystemProperties() // optional: allow end users to customize
                .ansi(CommandLine.Help.Ansi.ON)
                .build();
        CommandLine cli = new CommandLine(new RootCommand());
        cli.setColorScheme(colorScheme);
        cli.execute(args);
    }

}
