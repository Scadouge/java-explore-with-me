package ru.scadouge.ewm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.scadouge.stats.client.StatsClient;

@Configuration
public class StatsClientConfig {
    @Bean
    public StatsClient statsClient(WebClient webClient) {
        return new StatsClient(webClient);
    }

    @Bean
    public WebClient webClient(@Value("${stats-service.url}") String url) {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}
