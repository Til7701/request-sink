package de.holube.request_sink.cli;

import com.sun.net.httpserver.HttpServer;
import de.holube.request_sink.io.IO;
import de.holube.request_sink.server.Handler;
import picocli.CommandLine;

import java.io.IOException;
import java.net.InetSocketAddress;

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
public final class RootCommand implements Runnable {

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
    public void run() {
        try {
            runInternal();
        } catch (IOException e) {
            throw new RuntimeException(e); // NOSONAR
        }
    }

    private void runInternal() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer server = HttpServer.create();
        server.bind(address, 0);
        server.createContext("/", new Handler(statusCode));
        server.start();
        IO.println("Listening for requests on port " + port);
    }

}
