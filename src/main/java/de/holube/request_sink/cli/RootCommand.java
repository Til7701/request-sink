package de.holube.request_sink.cli;

import com.sun.net.httpserver.HttpServer;
import de.holube.request_sink.cli.mixins.DebugMixin;
import de.holube.request_sink.cli.providers.RootDefaultValueProvider;
import de.holube.request_sink.cli.providers.VersionProvider;
import de.holube.request_sink.io.HttpStatusCodeFormatter;
import de.holube.request_sink.io.PortFormatter;
import de.holube.request_sink.io.Terminal;
import de.holube.request_sink.server.Handler;
import de.holube.request_sink.validation.http.status_code.HttpStatusCode;
import de.holube.request_sink.validation.http.status_code.HttpStatusCodes;
import de.holube.request_sink.validation.port.Port;
import de.holube.request_sink.validation.port.Ports;
import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "request-sink",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        description = "A simple http server listening for requests and logging them.",
        defaultValueProvider = RootDefaultValueProvider.class,
        subcommands = {
                ConfigCommand.class,
        }
)
public final class RootCommand implements Callable<Integer> {

    @CommandLine.Mixin
    private DebugMixin debugMixin;

    @SuppressWarnings("unused")
    @CommandLine.Option(
            names = {
                    "--port",
                    "-p"
            },
            description = "The port to listen for requests on.",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private int port;

    @SuppressWarnings("unused")
    @CommandLine.Option(
            names = {
                    "--status-code",
                    "-s"
            },
            description = "The status code to return.",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private int statusCode;

    @Override
    public Integer call() {
        try {
            runInternal();
            awaitExitSignal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CommandLine.tracer().debug("Interrupted, shutting down...", e);
        } catch (BindException e) {
            String portInfo = PortFormatter.format(Ports.get(port));
            IO.println("Error: Could not bind to port " + portInfo + ": " + e.getMessage());
            return 1;
        } catch (IOException e) {
            throw new RuntimeException(e); // NOSONAR
        }
        return 0;
    }

    private void runInternal() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer server = HttpServer.create();
        CommandLine.tracer().debug("Binding to address %s", address);
        server.bind(address, 0);
        server.createContext("/", new Handler(statusCode));
        CommandLine.tracer().debug("Starting Server...");
        server.start();
        CommandLine.tracer().debug("Server started.");
        printStatusCodeNotice();
        printPortNotice();
    }

    private void printStatusCodeNotice() {
        if (statusCode < 100 || statusCode > 599) {
            IO.println("Warning: The provided status code " + statusCode + " is not a valid HTTP status code.");
        }
        HttpStatusCode httpStatusCode = HttpStatusCodes.get(statusCode);
        String message = HttpStatusCodeFormatter.format(httpStatusCode);
        IO.println("Responding with status code: " + message);
    }

    private void printPortNotice() {
        if (port < 0 || port > 65535) {
            IO.println("Warning: The provided port " + port + " is out of range (0-65535).");
        }
        Port portInfo = Ports.get(port);
        String formattedPort = PortFormatter.format(portInfo);
        IO.println("Listening for requests on port " + formattedPort);
    }

    private void awaitExitSignal() throws IOException, InterruptedException {
        CommandLine.tracer().debug("Preparing console for reading exit signal...");
        Terminal.setTerminalToCBreak();
        try {
            final InputStream in = System.in;
            while (!Thread.currentThread().isInterrupted()) {
                int input = in.read();
                if (input == 'q' || input == 'Q') {
                    CommandLine.tracer().debug("Quitting...");
                    return;
                }
            }
            CommandLine.tracer().debug("Interrupted while waiting for exit signal.");
        } finally {
            CommandLine.tracer().debug("Restoring console...");
            Terminal.reset();
        }
    }

}
