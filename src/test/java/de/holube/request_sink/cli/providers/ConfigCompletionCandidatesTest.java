package de.holube.request_sink.cli.providers;

import de.holube.request_sink.config.Pref;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigCompletionCandidatesTest {

    @Test
    void testIterator() {
        List<String> expectedKeys = new ArrayList<>(Pref.values().length);
        for (Pref pref : Pref.values()) {
            expectedKeys.add(pref.getKey());
        }
        ConfigCompletionCandidates candidates = new ConfigCompletionCandidates();

        List<String> actualKeys = new ArrayList<>();
        for (String key : candidates) {
            actualKeys.add(key);
        }

        assertEquals(expectedKeys, actualKeys);
    }

}
