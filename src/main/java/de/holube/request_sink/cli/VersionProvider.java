package de.holube.request_sink.cli;

import picocli.CommandLine;

public final class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        return new String[]{
                "request-sink v" + System.getProperty("request_sink.version")
        };
    }

}
