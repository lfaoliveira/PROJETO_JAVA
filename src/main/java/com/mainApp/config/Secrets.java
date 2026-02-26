package com.mainApp.config;

import io.github.cdimascio.dotenv.Dotenv;


public enum Secrets {

    INSTANCE;

    private final Dotenv entries;

    Secrets() {
        entries = Dotenv.load();
    }

    public String get(String key, String defaultValue) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Invalid key: " + key);
        }

        String value = entries.get(key);
        return value != null ? value : defaultValue;
    }

    public String get(String key) {
        return get(key, null);
    }

    public static boolean isValidKey(String key) {
        for (Keys k : Keys.values()) {
            if (k.name().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public enum Keys {
        PORT,
        TMDB_API_KEY,
        BACKEND_SECRET
    }
}

