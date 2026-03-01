package com.mainApp.middleware;

import com.mainApp.service.AccessLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessLoggerIntercept extends OncePerRequestFilter {

    private final AccessLogService accessLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Extrai informações da requisição
            String ipAddress = AccessLogService.getClientIp();
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null) {
                userAgent = "Unknown";
            }
            String requestPath = request.getRequestURI();
            String httpMethod = request.getMethod();

            // Chama o filtro da cadeia (continua o processamento)
            filterChain.doFilter(request, response);

            // Após a resposta, loga o acesso
            int statusCode = response.getStatus();
            accessLogService.logAccess(ipAddress, userAgent, requestPath, httpMethod, statusCode);

        } catch (IOException | ServletException e) {
            log.error("Error in AccessLogger middleware: {}", e.getMessage(), e);
            throw e;
        }
    }
}
