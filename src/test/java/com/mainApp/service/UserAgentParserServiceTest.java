package com.mainApp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("UserAgentParserService Tests")
public class UserAgentParserServiceTest {

    private UserAgentParserService userAgentParserService;

    @BeforeEach
    void setUp() {
        userAgentParserService = new UserAgentParserService();
    }

    @Test
    @DisplayName("Should extract Chrome browser name")
    void testExtractChromeBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Chrome");
        log.info("✓ Test passed: Chrome browser detected");
    }

    @Test
    @DisplayName("Should extract Firefox browser name")
    void testExtractFirefoxBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Firefox");
        log.info("✓ Test passed: Firefox browser detected");
    }

    @Test
    @DisplayName("Should extract Safari browser name")
    void testExtractSafariBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Version/17.1 Safari/537.36";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Safari");
        log.info("✓ Test passed: Safari browser detected");
    }

    @Test
    @DisplayName("Should extract Edge browser name")
    void testExtractEdgeBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Edge");
        log.info("✓ Test passed: Edge browser detected");
    }

    @Test
    @DisplayName("Should extract Opera browser name")
    void testExtractOperaBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 OPR/106.0.0.0";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Opera");
        log.info("✓ Test passed: Opera browser detected");
    }

    @Test
    @DisplayName("Should extract Brave browser name")
    void testExtractBraveBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Brave/1.71.120";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Brave");
        log.info("✓ Test passed: Brave browser detected");
    }

    @Test
    @DisplayName("Should return Unknown for unrecognized browser")
    void testUnknownBrowser() {
        // Arrange
        String userAgent = "SomeUnknownBrowser/1.0";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Unknown");
        log.info("✓ Test passed: Unknown browser handled correctly");
    }

    @Test
    @DisplayName("Should return Unknown for null User-Agent")
    void testNullUserAgent() {
        // Act
        String browserName = userAgentParserService.extractBrowserName(null);

        // Assert
        assertThat(browserName).isEqualTo("Unknown");
        log.info("✓ Test passed: Null User-Agent handled correctly");
    }

    @Test
    @DisplayName("Should return Unknown for empty User-Agent")
    void testEmptyUserAgent() {
        // Act
        String browserName = userAgentParserService.extractBrowserName("");

        // Assert
        assertThat(browserName).isEqualTo("Unknown");
        log.info("✓ Test passed: Empty User-Agent handled correctly");
    }

    @Test
    @DisplayName("Should extract Chrome version correctly")
    void testExtractChromeVersion() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.217 Safari/537.36";

        // Act
        String version = userAgentParserService.extractBrowserVersion(userAgent);

        // Assert
        assertThat(version).startsWith("120");
        log.info("✓ Test passed: Chrome version {} extracted correctly", version);
    }

    @Test
    @DisplayName("Should extract Firefox version correctly")
    void testExtractFirefoxVersion() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0.1";

        // Act
        String version = userAgentParserService.extractBrowserVersion(userAgent);

        // Assert
        assertThat(version).startsWith("121");
        log.info("✓ Test passed: Firefox version {} extracted correctly", version);
    }

    @Test
    @DisplayName("Should extract Edge version correctly")
    void testExtractEdgeVersion() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.2210.61";

        // Act
        String version = userAgentParserService.extractBrowserVersion(userAgent);

        // Assert
        assertThat(version).startsWith("120");
        log.info("✓ Test passed: Edge version {} extracted correctly", version);
    }

    @Test
    @DisplayName("Should return Unknown for version when browser not recognized")
    void testUnknownBrowserVersion() {
        // Arrange
        String userAgent = "UnknownBrowser/1.0";

        // Act
        String version = userAgentParserService.extractBrowserVersion(userAgent);

        // Assert
        assertThat(version).isEqualTo("Unknown");
        log.info("✓ Test passed: Unknown browser version handled correctly");
    }

    @Test
    @DisplayName("Should return Unknown for version when User-Agent is null")
    void testNullUserAgentVersion() {
        // Act
        String version = userAgentParserService.extractBrowserVersion(null);

        // Assert
        assertThat(version).isEqualTo("Unknown");
        log.info("✓ Test passed: Null User-Agent version handled correctly");
    }

    @Test
    @DisplayName("Should handle User-Agent with multiple browser names")
    void testUserAgentWithMultipleBrowserNames() {
        // Arrange - Chrome em um Edge User-Agent (Edge é priorizado)
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Edge"); // Edge deve ser detectado, não Chrome
        log.info("✓ Test passed: Edge detected correctly in Edge User-Agent (not Chrome)");
    }

    @Test
    @DisplayName("Should extract Internet Explorer browser name")
    void testExtractInternetExplorerBrowser() {
        // Arrange
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko";

        // Act
        String browserName = userAgentParserService.extractBrowserName(userAgent);

        // Assert
        assertThat(browserName).isEqualTo("Internet Explorer");
        log.info("✓ Test passed: Internet Explorer browser detected");
    }
}

