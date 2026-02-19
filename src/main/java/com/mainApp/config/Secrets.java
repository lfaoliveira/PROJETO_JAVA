package com.mainApp.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.cache.Cache;

import java.util.Set;


public class Secrets {
    private static Dotenv entries;

    public enum keys {
        PORT,
        TMDB_API_KEY,
        BACKEND_SECRET
    }

    public Secrets() {
        entries = Dotenv.load();
    }

    public String get(String key, String defaultValue) {
        boolean validKey = isValidKey(key);
        if (!validKey) {
            throw new IllegalArgumentException("Invalid key: " + key);
        }

        if (entries.get(key) != null) {
            return entries.get(key);
        } else {
            return defaultValue;
        }
    }

    public static boolean isValidKey(String key) {
        for (keys k : keys.values()) {
            if (k.name().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public String get(String key) {
        return get(key, null);
    }

}
