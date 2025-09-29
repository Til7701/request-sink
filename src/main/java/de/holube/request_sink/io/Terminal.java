package de.holube.request_sink.io;

import lombok.NoArgsConstructor;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

// https://darkcoding.net/software/non-blocking-console-io-is-not-possible/
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Terminal {

    private static final int DEFAULT_WIDTH = 80;

    private static final Supplier<ITerminal> terminalImpl = StableValue.supplier(() -> {
        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win") || osName.contains("mac")) {
            CommandLine.tracer().debug("Using DefaultConsole implementation for OS: %s", osName);
            return new DefaultTerminal();
        } else {
            CommandLine.tracer().debug("Using SttyConsole implementation for OS: %s", osName);
            return new SttyTerminal();
        }
    });

    public static int getWidth() {
        return terminalImpl.get().getWidth();
    }

    public static void setTerminalToCBreak() throws IOException, InterruptedException {
        terminalImpl.get().setTerminalToCBreak();
    }

    public static void reset() {
        terminalImpl.get().reset();
    }

    /**
     * Execute the specified command and return the output
     * (both stdout and stderr).
     */
    private static String exec(final String[] cmd) throws IOException, InterruptedException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Process p = Runtime.getRuntime().exec(cmd);
        int c;
        InputStream in = p.getInputStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        in = p.getErrorStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        p.waitFor();

        return bout.toString();
    }

    private sealed interface ITerminal permits DefaultTerminal, SttyTerminal {
        int getWidth();

        void setTerminalToCBreak() throws IOException, InterruptedException;

        void reset();
    }

    private static final class DefaultTerminal implements ITerminal {
        @Override
        public int getWidth() {
            CommandLine.Model.UsageMessageSpec usageMessageSpec = new CommandLine.Model.UsageMessageSpec();
            usageMessageSpec.autoWidth(true);
            return usageMessageSpec.width();
        }

        @Override
        public void setTerminalToCBreak() {
            // noop
        }

        @Override
        public void reset() {
            // noop
        }
    }

    private static final class SttyTerminal implements ITerminal {
        private String ttyConfig;

        @Override
        public int getWidth() {
            String output;
            try {
                output = stty("-a -F /dev/tty");
            } catch (IOException e) {
                CommandLine.tracer().warn("Exception getting terminal width", e);
                return DEFAULT_WIDTH;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                CommandLine.tracer().warn("Interrupted while getting terminal width", e);
                return DEFAULT_WIDTH;
            }
            String[] parts = output.split(";", 4);
            if (parts.length >= 4) {
                String part = parts[2].trim();
                if (part.startsWith("columns ")) {
                    String[] subParts = part.split(" ");
                    if (subParts.length >= 2) {
                        try {
                            return Integer.parseInt(subParts[1]);
                        } catch (NumberFormatException e) {
                            CommandLine.tracer().warn("Exception parsing terminal width", e);
                        }
                    }
                }
            }
            CommandLine.tracer().debug("Could not determine terminal width from stty output: %s", output);
            return DEFAULT_WIDTH;
        }

        @Override
        public void setTerminalToCBreak() throws IOException, InterruptedException {
            ttyConfig = stty("-g");

            // set the console to be character-buffered instead of line-buffered
            stty("-icanon min 1");

            // disable character echoing
            stty("-echo");
        }

        @Override
        public void reset() {
            try {
                stty(ttyConfig.trim());
            } catch (IOException e) {
                CommandLine.tracer().warn("Exception restoring tty config", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                CommandLine.tracer().warn("Interrupted while restoring tty config", e);
            }
        }

        /**
         * Execute the stty command with the specified arguments
         * against the current active terminal.
         */
        private static String stty(final String args) throws IOException, InterruptedException {
            String cmd = "stty " + args + " < /dev/tty";
            return exec(new String[]{
                    "sh",
                    "-c",
                    cmd
            });
        }
    }

}
