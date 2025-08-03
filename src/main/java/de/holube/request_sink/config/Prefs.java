package de.holube.request_sink.config;

import java.util.prefs.Preferences;

public class Prefs {

    private static final Preferences PREFERENCES = Preferences.userRoot().node("request-sink");

    private Prefs() {
        // Prevent instantiation
    }

    public static String get(Pref pref) {
        return PREFERENCES.get(pref.getKey(), pref.getDefaultValue());
    }

    public static void put(Pref pref, String value) {
        PREFERENCES.put(pref.getKey(), value);
    }

    public static void unset(Pref pref) {
        PREFERENCES.remove(pref.getKey());
    }

}
