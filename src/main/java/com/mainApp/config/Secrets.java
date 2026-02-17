package com.mainApp.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

import java.util.Set;


public class Secrets {
    final Dotenv entries;

    public Secrets() {
        this.entries = Dotenv.load();
    }

    public String get(String key) {
        return entries.get(key);
    }

    public Dotenv getEntries() {
        return entries;
    }

}
