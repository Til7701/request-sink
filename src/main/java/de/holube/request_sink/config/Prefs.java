package de.holube.request_sink.config;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.prefs.Preferences;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Prefs {

    private static final Preferences PREFERENCES = Preferences.userRoot().node("request-sink");

    public static String get(@NonNull Pref pref) {
        return PREFERENCES.get(pref.getKey(), pref.getDefaultValue());
    }

    public static void put(@NonNull Pref pref, @NonNull String value) {
        PREFERENCES.put(pref.getKey(), value);
    }

    public static void unset(@NonNull Pref pref) {
        PREFERENCES.remove(pref.getKey());
    }

}
