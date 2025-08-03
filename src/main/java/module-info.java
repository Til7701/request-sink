module request.sink {
    requires java.prefs;
    requires jdk.httpserver;
    requires info.picocli;
    requires static info.picocli.codegen;

    opens de.holube.request_sink.cli to info.picocli;
    opens de.holube.request_sink.cli.mixins to info.picocli;
    opens de.holube.request_sink.cli.completion to info.picocli;

    exports de.holube.request_sink;
}