module request.sink {
    requires jdk.httpserver;
    requires info.picocli;

    opens de.holube.request_sink.cli to info.picocli;

    exports de.holube.request_sink;
}