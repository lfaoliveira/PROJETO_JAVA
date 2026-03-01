package com.mainApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAgentParserService {

    /**
     * Extrai o nome do navegador do User Agent
     */
    public String extractBrowserName(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }

        // Ordem importa: verificar browsers mais específicos primeiro
        if (userAgent.contains("Edg/") || userAgent.contains("Edg ")) {
            return "Edge";
        } else if (userAgent.contains("OPR/") || userAgent.contains("Opera/")) {
            return "Opera";
        } else if (userAgent.contains("Brave")) {
            return "Brave";
        } else if (userAgent.contains("Chrome") && !userAgent.contains("Chromium")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari")) {
            return "Safari";
        } else if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";
        } else {
            return "Unknown";
        }
    }

    /**
     * Extrai a versão do navegador do User Agent
     */
    public String extractBrowserVersion(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }

        try {
            String browserName = extractBrowserName(userAgent);

            switch (browserName) {
                case "Chrome":
                    return extractVersion(userAgent, "Chrome/");
                case "Firefox":
                    return extractVersion(userAgent, "Firefox/");
                case "Safari":
                    return extractVersion(userAgent, "Version/");
                case "Edge":
                    return extractVersion(userAgent, "Edg/");
                case "Opera":
                case "OPR":
                    return extractVersion(userAgent, "OPR/");
                default:
                    return "Unknown";
            }
        } catch (Exception e) {
            log.warn("Error parsing browser version from User Agent: {}", userAgent, e);
            return "Unknown";
        }
    }

    /**
     * Extrai a versão a partir de um padrão no User Agent
     */
    private String extractVersion(String userAgent, String pattern) {
        int index = userAgent.indexOf(pattern);
        if (index != -1) {
            String version = userAgent.substring(index + pattern.length());
            int endIndex = version.indexOf(" ");
            if (endIndex != -1) {
                version = version.substring(0, endIndex);
            }
            return version.split(" ")[0]; // Pega apenas a primeira parte da versão
        }
        return "Unknown";
    }
}

