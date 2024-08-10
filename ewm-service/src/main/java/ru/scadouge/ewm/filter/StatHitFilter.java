package ru.scadouge.ewm.filter;

import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.scadouge.stats.client.StatsClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatHitFilter extends OncePerRequestFilter {
    private final StatsClient statsClient;

    @Value("${app.name}")
    private static final String APP = "ewm-service";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                String uri = super.getRequestURI();
                if (uri.endsWith("/")) {
                    return uri.substring(0, uri.length() - 1);
                }
                return uri;
            }
        };

        filterChain.doFilter(wrapper, response);

        if (response.getStatus() / 200 == 1) {
            String ip = request.getRemoteAddr();
            String uri = request.getRequestURI();
            statsClient.sendStatHit(APP, uri, ip, LocalDateTime.now());
        }
    }
}
