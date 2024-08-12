package ru.scadouge.ewm.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.scadouge.stats.client.StatsClient;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StatHitFilter extends OncePerRequestFilter {
    private final StatsClient statsClient;

    @Value("${app.name}")
    private static final String APP = "ewm-service";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);

        if (response.getStatus() / 200 == 1) {
            String ip = request.getRemoteAddr();
            String uri = request.getRequestURI();
            statsClient.sendStatHit(APP, uri, ip, LocalDateTime.now());
        }
    }
}
