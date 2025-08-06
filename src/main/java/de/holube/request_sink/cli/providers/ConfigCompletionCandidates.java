package de.holube.request_sink.cli.providers;

import de.holube.request_sink.config.Pref;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Provides completion candidates for configuration options.
 * The available candidates are the keys of the preferences defined in the {@link Pref} enum.
 */
public final class ConfigCompletionCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(Pref.values())
                .map(Pref::getKey)
                .iterator();
    }

}
