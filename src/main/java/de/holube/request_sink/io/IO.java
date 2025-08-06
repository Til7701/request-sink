package de.holube.request_sink.io;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class IO {

    public static void println(String message) {
        System.out.println(message); // NOSONAR
    }

    public static void print(String message) {
        System.out.print(message); // NOSONAR
    }

}
