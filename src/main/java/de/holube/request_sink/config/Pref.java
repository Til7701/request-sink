package de.holube.request_sink.config;

import java.util.Optional;

public enum Pref {

    PORT("port", "8080"),
    STATUS_CODE("status_code", "200");

    private final String key;
    private final String defaultValue;

    Pref(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public static Optional<Pref> fromKey(String key) {
        for (Pref pref : values()) {
            if (pref.key.equals(key)) {
                return Optional.of(pref);
            }
        }
        return Optional.empty();
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

}
