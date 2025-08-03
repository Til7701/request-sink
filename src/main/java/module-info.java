module request.sink {
    requires jdk.httpserver;
    requires info.picocli;
    requires static info.picocli.codegen;

    opens de.holube.request_sink.cli to info.picocli;

    exports de.holube.request_sink;
}