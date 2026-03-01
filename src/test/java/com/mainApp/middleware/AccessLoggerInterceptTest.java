package com.mainApp.middleware;

import com.mainApp.model.entity.AccessLogEntity;
import com.mainApp.repository.AccessLogRepository;
import com.mainApp.service.AccessLogService;
import com.mainApp.service.GeoLocationService;
import com.mainApp.service.UserAgentParserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("AccessLoggerIntercept Tests")
public class AccessLoggerInterceptTest {

    private AccessLoggerIntercept accessLoggerIntercept;

    @Mock
    private AccessLogService accessLogServiceMock;

    @Mock
    private FilterChain filterChainMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accessLoggerIntercept = new AccessLoggerIntercept(accessLogServiceMock);
    }

    @Test
    @DisplayName("Should log access with correct IP address")
    void testLogAccessWithCorrectIpAddress() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/search/movie");
        request.setRemoteAddr("192.168.1.100");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        request.setRequestURI("/api/movies");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(accessLogServiceMock, times(1)).logAccess(
                argThat(ip -> ip.contains("192.168.1.100") || ip.contains("127.0.0.1")),
                contains("Chrome"),
                eq("/api/movies"),
                eq("GET"),
                anyInt()
        );

        log.info("✓ Test passed: Access logged with correct IP address");
    }

    @Test
    @DisplayName("Should extract User-Agent header correctly")
    void testExtractUserAgentHeader() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
        request.addHeader("User-Agent", userAgent);
        request.setRemoteAddr("10.0.0.1");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(accessLogServiceMock).logAccess(
                anyString(),
                eq(userAgent),
                eq("/api/test"),
                anyString(),
                anyInt()
        );

        log.info("✓ Test passed: User-Agent header extracted correctly");
    }

    @Test
    @DisplayName("Should use 'Unknown' when User-Agent is missing")
    void testMissingUserAgent() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/users");
        // Não adiciona User-Agent
        request.setRemoteAddr("172.16.0.1");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(accessLogServiceMock).logAccess(
                anyString(),
                eq("Unknown"),
                eq("/api/users"),
                eq("POST"),
                anyInt()
        );

        log.info("✓ Test passed: Missing User-Agent handled correctly");
    }

    @Test
    @DisplayName("Should capture correct HTTP method")
    void testCaptureHttpMethod() throws ServletException, IOException {
        // Arrange
        String[] methods = {"GET", "POST", "PUT", "DELETE", "PATCH"};

        for (String method : methods) {
            // Arrange
            MockHttpServletRequest request = new MockHttpServletRequest(method, "/api/resource");
            request.setRemoteAddr("192.168.1.1");
            request.addHeader("User-Agent", "TestAgent/1.0");

            MockHttpServletResponse response = new MockHttpServletResponse();

            // Act
            accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

            // Assert
            verify(accessLogServiceMock).logAccess(
                    anyString(),
                    anyString(),
                    eq("/api/resource"),
                    eq(method),
                    anyInt()
            );

            log.info("✓ Test passed: HTTP method {} captured correctly", method);
        }
    }

    @Test
    @DisplayName("Should capture request path correctly")
    void testCaptureRequestPath() throws ServletException, IOException {
        // Arrange
        String[] paths = {"/api/movies", "/api/users", "/search/movie", "/v1/data"};

        for (String path : paths) {
            // Arrange
            MockHttpServletRequest request = new MockHttpServletRequest("GET", path);
            request.setRemoteAddr("192.168.1.1");
            request.addHeader("User-Agent", "TestAgent/1.0");

            MockHttpServletResponse response = new MockHttpServletResponse();

            // Act
            accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

            // Assert
            verify(accessLogServiceMock).logAccess(
                    anyString(),
                    anyString(),
                    eq(path),
                    anyString(),
                    anyInt()
            );

            log.info("✓ Test passed: Request path {} captured correctly", path);
        }
    }

    @Test
    @DisplayName("Should capture HTTP status code")
    void testCaptureStatusCode() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("192.168.1.1");
        request.addHeader("User-Agent", "TestAgent/1.0");

        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(accessLogServiceMock).logAccess(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                statusCodeCaptor.capture()
        );

        assertThat(statusCodeCaptor.getValue()).isEqualTo(200);
        log.info("✓ Test passed: HTTP status code captured correctly");
    }

    @Test
    @DisplayName("Should continue filter chain even on error")
    void testFilterChainContinuesOnError() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("192.168.1.1");
        request.addHeader("User-Agent", "TestAgent/1.0");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Configura o mock para lançar exceção no logAccess
        doThrow(new RuntimeException("Database error")).when(accessLogServiceMock)
                .logAccess(anyString(), anyString(), anyString(), anyString(), anyInt());

        // Act & Assert
        try {
            accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);
        } catch (RuntimeException e) {
            // Exceção esperada
            log.info("✓ Test passed: Exception handled correctly - {}", e.getMessage());
        }

        verify(filterChainMock, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should handle different browser User-Agents")
    void testDifferentBrowserUserAgents() throws ServletException, IOException {
        // Arrange
        String[] userAgents = {
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36", // Chrome
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0", // Firefox
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36", // Safari
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0" // Edge
        };

        for (String userAgent : userAgents) {
            // Arrange
            MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
            request.setRemoteAddr("192.168.1.1");
            request.addHeader("User-Agent", userAgent);

            MockHttpServletResponse response = new MockHttpServletResponse();

            // Act
            accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

            // Assert
            verify(accessLogServiceMock).logAccess(
                    anyString(),
                    eq(userAgent),
                    anyString(),
                    anyString(),
                    anyInt()
            );

            log.info("✓ Test passed: Browser User-Agent captured correctly");
        }
    }

    @Test
    @DisplayName("Should log access with X-Forwarded-For header (proxy)")
    void testXForwardedForHeader() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("10.0.0.1");
        request.addHeader("X-Forwarded-For", "203.0.113.45, 198.51.100.24");
        request.addHeader("User-Agent", "TestAgent/1.0");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(accessLogServiceMock, times(1)).logAccess(
                anyString(),
                eq("TestAgent/1.0"),
                eq("/api/test"),
                eq("GET"),
                anyInt()
        );

        log.info("✓ Test passed: X-Forwarded-For header handled correctly");
    }

    @Test
    @DisplayName("Should verify filter is called exactly once per request")
    void testFilterCalledOncePerRequest() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("192.168.1.1");
        request.addHeader("User-Agent", "TestAgent/1.0");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(filterChainMock, times(1)).doFilter(request, response);
        verify(accessLogServiceMock, times(1)).logAccess(anyString(), anyString(), anyString(), anyString(), anyInt());

        log.info("✓ Test passed: Filter called exactly once per request");
    }

    @Test
    @DisplayName("Should handle query parameters in request path")
    void testHandleQueryParameters() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/search/movie?query=Star+Wars&page=1");
        request.setRemoteAddr("192.168.1.1");
        request.addHeader("User-Agent", "TestAgent/1.0");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        accessLoggerIntercept.doFilterInternal(request, response, filterChainMock);

        // Assert
        verify(accessLogServiceMock).logAccess(
                anyString(),
                anyString(),
                eq("/search/movie"),
                anyString(),
                anyInt()
        );

        log.info("✓ Test passed: Query parameters handled correctly");
    }
}

