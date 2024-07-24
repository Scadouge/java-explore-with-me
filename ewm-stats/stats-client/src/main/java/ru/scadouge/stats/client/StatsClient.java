package ru.scadouge.stats.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import ru.scadouge.stats.dto.EndpointHit;
import ru.scadouge.stats.dto.Utils;
import ru.scadouge.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StatsClient {
    private final WebClient webClient;

    public void sendStatHit(String app, String uri, String ip, String timestamp) {
        EndpointHit endpointHit = EndpointHit.builder().app(app).ip(ip).timestamp(timestamp).uri(uri).build();
        log.info("Отправлен запрос на запись статистики endpointHit={}", endpointHit);

        webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(endpointHit)
                .retrieve()
                .toEntity(String.class)
                .doOnError(c -> log.warn(c.getMessage()))
                .onErrorComplete()
                .block();
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Отправлен запрос на получение статистики start={}, end={}, unique={}, uris={}", start, end, unique, uris);

        ResponseEntity<List<ViewStats>> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start.format(Utils.DATE_TIME_FORMATTER))
                        .queryParam("end", end.format(Utils.DATE_TIME_FORMATTER))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .toEntityList(ViewStats.class)
                .doOnError(c -> log.warn(c.getMessage()))
                .onErrorReturn(ResponseEntity.ok(List.of()))
                .block();
        if (response != null) {
            return response.getBody();
        } else {
            return List.of();
        }
    }
}
