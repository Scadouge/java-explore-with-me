package ru.scadouge.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.stats.args.ArgsMapper;
import ru.scadouge.stats.args.GetStatsArgs;
import ru.scadouge.stats.dto.EndpointHit;
import ru.scadouge.stats.dto.ViewStats;
import ru.scadouge.stats.model.HitMapper;
import ru.scadouge.stats.service.StatsService;
import ru.scadouge.stats.validation.ValidSearchDateInterval;
import ru.scadouge.stats.view.StatCountView;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService statsService;
    private final HitMapper hitMapper;
    private final ArgsMapper argsMapper;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody EndpointHit endpointHit) {
        log.info("Получен запрос на запись статистики endpointHit={}", endpointHit);
        statsService.hit(hitMapper.toModel(endpointHit));
    }

    @GetMapping("/stats")
    @ValidSearchDateInterval
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        GetStatsArgs statsArgs = argsMapper.toGetStatsArgs(start, end, uris, unique);
        log.info("Получен запрос на получение статистики statsArgs={}", statsArgs);
        List<StatCountView> stats = statsService.getStats(statsArgs);
        return hitMapper.toViewStatsDto(stats);
    }

    @GetMapping("/stats/views")
    public List<ViewStats> getViewsStats(@RequestParam List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики uris={}, unique={}", uris, unique);
        List<StatCountView> stats = statsService.getViewsStats(uris, unique);
        return hitMapper.toViewStatsDto(stats);
    }
}
