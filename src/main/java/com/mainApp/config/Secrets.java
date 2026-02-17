package com.mainApp.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

import java.util.Set;


public class Secrets {
    private static Dotenv entries;

    private Secrets() {

    }

    public static String get(String key) {
        if (entries == null) {
            entries = Dotenv.load();
        }
        return entries.get(key);
    }

}
