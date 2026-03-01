package com.mainApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GeoLocationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> countryCache = new HashMap<>();

    /**
     * Obtém o país a partir do endereço IP usando a API ip-api.com
     */
    public String getCountryByIp(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty() || isLocalIp(ipAddress)) {
            return "Local";
        }

        // Verifica o cache
        if (countryCache.containsKey(ipAddress)) {
            return countryCache.get(ipAddress);
        }

        try {
            String url = "http://ip-api.com/json/" + ipAddress + "?fields=country";
            String response = restTemplate.getForObject(url, String.class);

            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode countryNode = jsonNode.get("country");

            // Verifica se o nó existe e não é nulo antes de chamar asText()
            if (countryNode != null && !countryNode.isNull()) {
                String country = countryNode.asText();
                if (country != null && !country.isEmpty()) {
                    countryCache.put(ipAddress, country);
                    return country;
                }
            } else {
                log.warn("Country field is null or missing in response for IP: {}", ipAddress);
            }
        } catch (RestClientException | com.fasterxml.jackson.core.JsonProcessingException e) {
            log.warn("Error fetching country for IP: {} - {}", ipAddress, e.getMessage());
        }

        return "Unknown";
    }

    /**
     * Verifica se o IP é local
     */
    private boolean isLocalIp(String ipAddress) {
        return ipAddress.startsWith("127.") ||
               ipAddress.startsWith("192.168.") ||
               ipAddress.startsWith("10.") ||
               ipAddress.startsWith("172.") ||
               ipAddress.equals("localhost") ||
               ipAddress.equals("::1");
    }
}

