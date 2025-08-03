package de.holube.request_sink.cli.completion;

import de.holube.request_sink.config.Pref;

import java.util.Arrays;
import java.util.Iterator;

public class ConfigCompletionCandidates implements Iterable<String> {
    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(Pref.values())
                .map(Pref::getKey)
                .iterator();
    }
}
