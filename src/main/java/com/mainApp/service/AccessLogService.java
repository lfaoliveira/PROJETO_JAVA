package com.mainApp.service;

import com.mainApp.model.entity.AccessLogEntity;
import com.mainApp.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final GeoLocationService geoLocationService;
    private final UserAgentParserService userAgentParserService;

    /**
     * Loga um acesso no banco de dados com informações de IP, país e User Agent
     */
    public void logAccess(String ipAddress, String userAgent, String requestPath, String httpMethod, Integer statusCode) {
        try {
            String country = geoLocationService.getCountryByIp(ipAddress);
            String browserName = userAgentParserService.extractBrowserName(userAgent);
            String browserVersion = userAgentParserService.extractBrowserVersion(userAgent);

            AccessLogEntity accessLog = new AccessLogEntity();
            accessLog.setIpAddress(ipAddress);
            accessLog.setCountry(country);
            accessLog.setUserAgent(userAgent);
            accessLog.setBrowserName(browserName);
            accessLog.setBrowserVersion(browserVersion);
            accessLog.setRequestPath(requestPath);
            accessLog.setHttpMethod(httpMethod);
            accessLog.setStatusCode(statusCode);
            accessLog.setTimestamp(LocalDateTime.now());

            accessLogRepository.save(accessLog);

            log.info("Access logged: IP={}, Country={}, Browser={} {}, Path={}, Method={}",
                    ipAddress, country, browserName, browserVersion, requestPath, httpMethod);
        } catch (Exception e) {
            log.error("Error logging access: {}", e.getMessage(), e);
        }
    }

    /**
     * Extrai o IP do cliente da requisição (considerando proxies)
     */
    public static String getClientIp() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) getRequestAttributes();
        if (attrs == null) {
            return "Unknown";
        }

        String ip = attrs.getRequest().getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = attrs.getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = attrs.getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = attrs.getRequest().getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = attrs.getRequest().getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = attrs.getRequest().getRemoteAddr();
        }

        // Se X-Forwarded-For contém múltiplos IPs, pega o primeiro
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip != null ? ip : "Unknown";
    }
}

