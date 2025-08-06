package de.holube.request_sink.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Enum representing the configuration preferences for the request sink server.
 * Each preference has a key and a default value.
 * The key is used to identify the preference in the config file and the command line interface.
 * The name of the enum may differ from the key, but should still be descriptive of the preference's purpose.
 */
@Getter
@RequiredArgsConstructor
public enum Pref {

    PORT("port", "8080"),
    STATUS_CODE("status_code", "200");

    @NonNull
    private final String key;
    @NonNull
    private final String defaultValue;

    /**
     * Retrieves a preference by its key.
     *
     * @param key the key of the preference
     * @return an Optional containing the Pref if found, or empty if not found
     */
    public static Optional<Pref> fromKey(@NonNull String key) {
        for (Pref pref : values()) {
            if (pref.key.equals(key)) {
                return Optional.of(pref);
            }
        }
        return Optional.empty();
    }

}
