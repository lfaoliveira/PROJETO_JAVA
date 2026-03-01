package com.mainApp.middleware;

import com.mainApp.model.entity.AccessLogEntity;
import com.mainApp.repository.AccessLogRepository;
import com.mainApp.service.AccessLogService;
import com.mainApp.service.GeoLocationService;
import com.mainApp.service.UserAgentParserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import({GeoLocationService.class, UserAgentParserService.class, AccessLogService.class})
@DisplayName("AccessLogEntity Integration Tests")
public class AccessLoggerIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @BeforeEach
    void setUp() {
        // Limpa os dados antes de cada teste
        accessLogRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save access log to database")
    void testSaveAccessLog() {
        // Arrange
        AccessLogEntity accessLog = new AccessLogEntity();
        accessLog.setIpAddress("192.168.1.100");
        accessLog.setCountry("Brazil");
        accessLog.setUserAgent("Mozilla/5.0 Chrome/120.0.0.0");
        accessLog.setBrowserName("Chrome");
        accessLog.setBrowserVersion("120.0.0.0");
        accessLog.setRequestPath("/api/movies");
        accessLog.setHttpMethod("GET");
        accessLog.setStatusCode(200);
        accessLog.setTimestamp(LocalDateTime.now());

        // Act
        AccessLogEntity saved = accessLogRepository.save(accessLog);
        entityManager.flush();

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getIpAddress()).isEqualTo("192.168.1.100");
        assertThat(saved.getCountry()).isEqualTo("Brazil");
        assertThat(saved.getBrowserName()).isEqualTo("Chrome");

        log.info("✓ Test passed: Access log saved to database with ID: {}", saved.getId());
    }

    @Test
    @DisplayName("Should retrieve all access logs from database")
    void testRetrieveAccessLogs() {
        // Arrange
        for (int i = 0; i < 5; i++) {
            AccessLogEntity accessLog = new AccessLogEntity();
            accessLog.setIpAddress("192.168.1." + i);
            accessLog.setCountry("Country" + i);
            accessLog.setUserAgent("Agent" + i);
            accessLog.setBrowserName("Browser" + i);
            accessLog.setBrowserVersion("1.0");
            accessLog.setRequestPath("/api/test" + i);
            accessLog.setHttpMethod("GET");
            accessLog.setStatusCode(200);
            accessLog.setTimestamp(LocalDateTime.now());

            accessLogRepository.save(accessLog);
        }
        entityManager.flush();

        // Act
        List<AccessLogEntity> logs = accessLogRepository.findAll();

        // Assert
        assertThat(logs).hasSize(5);
        assertThat(logs.get(0).getIpAddress()).startsWith("192.168.1.");

        log.info("✓ Test passed: Retrieved {} access logs from database", logs.size());
    }

    @Test
    @DisplayName("Should save access log with correct HTTP status codes")
    void testAccessLogWithDifferentStatusCodes() {
        // Arrange
        int[] statusCodes = {200, 201, 400, 401, 403, 404, 500, 502};

        for (int statusCode : statusCodes) {
            AccessLogEntity accessLog = new AccessLogEntity();
            accessLog.setIpAddress("192.168.1.1");
            accessLog.setCountry("TestCountry");
            accessLog.setUserAgent("TestAgent");
            accessLog.setBrowserName("TestBrowser");
            accessLog.setBrowserVersion("1.0");
            accessLog.setRequestPath("/api/test");
            accessLog.setHttpMethod("GET");
            accessLog.setStatusCode(statusCode);
            accessLog.setTimestamp(LocalDateTime.now());

            // Act
            accessLogRepository.save(accessLog);
        }
        entityManager.flush();

        // Assert
        List<AccessLogEntity> allLogs = accessLogRepository.findAll();
        assertThat(allLogs).hasSize(8);
        assertThat(allLogs.stream().map(AccessLogEntity::getStatusCode))
                .contains(200, 201, 400, 401, 403, 404, 500, 502);

        log.info("✓ Test passed: Access logs saved with {} different status codes", statusCodes.length);
    }

    @Test
    @DisplayName("Should store all required fields correctly")
    void testAccessLogAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        AccessLogEntity accessLog = new AccessLogEntity();
        accessLog.setIpAddress("203.0.113.45");
        accessLog.setCountry("United States");
        accessLog.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        accessLog.setBrowserName("Chrome");
        accessLog.setBrowserVersion("120.0.0.0");
        accessLog.setRequestPath("/search/movie?query=StarWars");
        accessLog.setHttpMethod("POST");
        accessLog.setStatusCode(201);
        accessLog.setTimestamp(now);

        // Act
        AccessLogEntity saved = accessLogRepository.save(accessLog);
        entityManager.flush();
        entityManager.clear();

        // Retrieve e verify
        AccessLogEntity retrieved = accessLogRepository.findById(saved.getId()).orElse(null);

        // Assert
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getIpAddress()).isEqualTo("203.0.113.45");
        assertThat(retrieved.getCountry()).isEqualTo("United States");
        assertThat(retrieved.getUserAgent()).contains("Mozilla/5.0");
        assertThat(retrieved.getBrowserName()).isEqualTo("Chrome");
        assertThat(retrieved.getBrowserVersion()).isEqualTo("120.0.0.0");
        assertThat(retrieved.getRequestPath()).isEqualTo("/search/movie?query=StarWars");
        assertThat(retrieved.getHttpMethod()).isEqualTo("POST");
        assertThat(retrieved.getStatusCode()).isEqualTo(201);
        assertThat(retrieved.getTimestamp()).isNotNull();

        log.info("✓ Test passed: All fields stored and retrieved correctly");
    }

    @Test
    @DisplayName("Should count total access logs")
    void testCountAccessLogs() {
        // Arrange
        for (int i = 0; i < 3; i++) {
            AccessLogEntity accessLog = new AccessLogEntity();
            accessLog.setIpAddress("192.168.1." + i);
            accessLog.setCountry("Country");
            accessLog.setUserAgent("Agent");
            accessLog.setBrowserName("Browser");
            accessLog.setBrowserVersion("1.0");
            accessLog.setRequestPath("/api/test");
            accessLog.setHttpMethod("GET");
            accessLog.setStatusCode(200);
            accessLog.setTimestamp(LocalDateTime.now());

            accessLogRepository.save(accessLog);
        }
        entityManager.flush();

        // Act
        long count = accessLogRepository.count();

        // Assert
        assertThat(count).isEqualTo(3);

        log.info("✓ Test passed: Total access logs count is {}", count);
    }

    @Test
    @DisplayName("Should handle special characters in User-Agent")
    void testSpecialCharactersInUserAgent() {
        // Arrange
        String userAgentWithSpecialChars = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0 @#$%&*";

        AccessLogEntity accessLog = new AccessLogEntity();
        accessLog.setIpAddress("192.168.1.1");
        accessLog.setCountry("TestCountry");
        accessLog.setUserAgent(userAgentWithSpecialChars);
        accessLog.setBrowserName("Chrome");
        accessLog.setBrowserVersion("120.0.0.0");
        accessLog.setRequestPath("/api/test");
        accessLog.setHttpMethod("GET");
        accessLog.setStatusCode(200);
        accessLog.setTimestamp(LocalDateTime.now());

        // Act
        AccessLogEntity saved = accessLogRepository.save(accessLog);
        entityManager.flush();
        entityManager.clear();

        // Assert
        AccessLogEntity retrieved = accessLogRepository.findById(saved.getId()).orElse(null);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getUserAgent()).isEqualTo(userAgentWithSpecialChars);

        log.info("✓ Test passed: Special characters in User-Agent handled correctly");
    }
}

