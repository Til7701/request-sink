package de.holube.request_sink.config;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrefTest {

    @Test
    void testKeyDuplicates() {
        Pref[] values = Pref.values();
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals(values[i].getKey(), values[j].getKey(),
                        "Duplicate key found: " + values[i].getKey());
            }
        }
    }

    @Test
    void testNonNulls() {
        for (Pref pref : Pref.values()) {
            assertNotNull(pref.getKey(), "Key should not be null for " + pref);
            assertNotNull(pref.getDefaultValue(), "Default value should not be null for " + pref);
        }
    }

    @Test
    void testBlankKeys() {
        for (Pref pref : Pref.values()) {
            assertFalse(pref.getKey().isBlank(), "Key should not be blank for " + pref);
        }
    }

    @Test
    void testFromKey() {
        for (Pref pref : Pref.values()) {
            Optional<Pref> fromKey = Pref.fromKey(pref.getKey());
            assertTrue(fromKey.isPresent());
            assertEquals(pref, fromKey.get(), "fromKey should return the correct Pref for key: " + pref.getKey());
        }
    }

    @Test
    void testFromKeyInvalid() {
        Optional<Pref> fromKey = Pref.fromKey("non_existent_key");
        assertTrue(fromKey.isEmpty(), "fromKey should return empty for non-existent key");
    }

    @Test
    void testFromKeyNull() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> Pref.fromKey(null),
                "fromKey should throw NullPointerException for null key");
    }

}
